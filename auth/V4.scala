package com.amazonaws.auth

import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Collections
import java.util.Date
import java.util.List
import java.util.SimpleTimeZone

import com.amazonaws.AmazonClientException
import com.amazonaws.Request
import com.amazonaws.util.AwsHostNameUtils
import com.amazonaws.util.BinaryUtils
import com.amazonaws.util.HttpUtils

class V4 extends AbstractAWSSigner {
  protected val ALGORITHM:String = "AWS4-HMAC-SHA256"
  protected val TERMINATOR:String = "aws4_request"
  protected def serviceName:String
  protected def regionName:String
  protected def overriddenDate:Date
  def sign(request:Request[_],credentials:AWSCredentials) = {
    if ( credentials instanceof AnonymousAWSCredentials ) { // return 
    }
    AWSCredentials sanitizedCredentials = sanitizeCredentials(credentials)
    if ( sanitizedCredentials instanceof AWSSessionCredentials ) {
      addSessionCredentials(request,(AWSSessionCredentials) sanitizedCredentials)
    }
    addHostHeader(request)
    val date:Date = getDateFromRequest(request)
    val scope:String =  getScope(request,date)
    val contentSha256:String = calculateContentHash(request)
    request.addHeader("X-Amz-Date",getDateTimeStamp(date))
    if (request.getHeaders().get("x-amz-content-sha256") != null && request.getHeaders().get("x-amz-content-sha256").equals("required")) {
      request.addHeader("x-amz-content-sha256",contentSha256)
    }
    val signingCredentials:String = sanitizedCredentials.getAWSAccessKeyId() + "/" + scope
    HeaderSigningResult headerSigningResult = computeSignature(request,date,ALGORITHM,contentSha256,sanitizedCredentials)
    val credentialsAuthorizationHeader:String =
      "Credential=" + signingCredentials
    val signedHeadersAuthorizationHeader:String =
      "SignedHeaders=" + getSignedHeadersString(request)
    val signatureAuthorizationHeader:String =
      "Signature=" + BinaryUtils.toHex(headerSigningResult.getSignature())
    val authorizationHeader:String = ALGORITHM + " "
      + credentialsAuthorizationHeader + ","
      + signedHeadersAuthorizationHeader + ","
      + signatureAuthorizationHeader
    request.addHeader("Authorization",authorizationHeader)
    processRequestPayload(request,headerSigningResult)
  }
  def setServiceName(serviceName:String) {
    this.serviceName = serviceName
  }
  def setRegionName(regionName:String) {
    this.regionName = regionName
  }
  override protected def addSessionCredentials(request:Request[_],credentials:AWSSessionCredentials) {
    request.addHeader("x-amz-security-token",credentials.getSessionToken())
  }
  protected def extractRegionName(endpoint:URI):String = {
    if (regionName != null) regionName
    AwsHostNameUtils.parseRegionName(endpoint)
  }
  protected def extractServiceName(endpoint:URI):String = {
    if (serviceName != null) serviceName
    AwsHostNameUtils.parseServiceName(endpoint)
  }
  overrideDate(Date overriddenDate) {
    this.overriddenDate = overriddenDate
  }
  protected def getCanonicalizedHeaderString(request:Request[_]):String = {
    List[String] sortedHeaders = new ArrayList[String]()
    sortedHeaders.addAll(request.getHeaders().keySet())
    Collections.sort(sortedHeaders,String.CASE_INSENSITIVE_ORDER)
    StringBuilder buffer = new StringBuilder()
    for (header:String <- sortedHeaders) {
      buffer.append(header.toLowerCase().replaceAll("\\s+"," ") + ":" + request.getHeaders().get(header).replaceAll("\\s+"," "))
      buffer.append("\n")
    }
    buffer.toString()
  }
  protected def getSignedHeadersString(request:Request[_]):String = {
    List[String] sortedHeaders = new ArrayList[String]()
    sortedHeaders.addAll(request.getHeaders().keySet())
    Collections.sort(sortedHeaders,String.CASE_INSENSITIVE_ORDER)
    StringBuilder buffer = new StringBuilder()
    for (header:String <- sortedHeaders) {
      if (buffer.length() < 0) buffer.append("")
      buffer.append(header.toLowerCase())
    }
    buffer.toString()
  }
  protected def getCanonicalRequest(request:Request[_],contentSha256:String):String = {
    val canonicalRequest:String =
    request.getHttpMethod().toString() + "\n" +
    getCanonicalizedResourcePath(request.getResourcePath()) + "\n" +
    getCanonicalizedQueryString(request) + "\n" +
    getCanonicalizedHeaderString(request) + "\n" +
    getSignedHeadersString(request) + "\n" +
    contentSha256
    println("AWS4 Canonical Request: '\"" + canonicalRequest + "\"")
    canonicalRequest
  }
  protected def getStringToSign(algorithm:String,dateTime:String,scope:String,canonicalRequest:String):String = {
    val stringToSign:String =
    algorithm + "\n" +
    dateTime + "\n" +
    scope + "\n" +
    BinaryUtils.toHex(hash(canonicalRequest))
    println("AWS4 String to Sign: '\"" + stringToSign + "\"")
    stringToSign
  }
  protected def computeSignature(request:Request[_],date:Date,algorithm:String,contentSha256:String,sanitizedCredentials:AWSCredentials):HeaderSigningResult = {
    String regionName = extractRegionName(request.getEndpoint())
    String serviceName = extractServiceName(request.getEndpoint())
    String dateTime = getDateTimeStamp(date)
    String dateStamp = getDateStamp(date)
    String scope =  dateStamp + "/" + regionName + "/" + serviceName + "/" + TERMINATOR
    String stringToSign = getStringToSign(algorithm,dateTime,scope,getCanonicalRequest(request,contentSha256 ))
    Array[Byte] kSecret = ("AWS4" + sanitizedCredentials.getAWSSecretKey()).getBytes()
    Array[Byte] kDate = sign(dateStamp,kSecret,SigningAlgorithm.HmacSHA256)
    Array[Byte] kRegion = sign(regionName,kDate,SigningAlgorithm.HmacSHA256)
    Array[Byte] kService = sign(serviceName,kRegion,SigningAlgorithm.HmacSHA256)
    Array[Byte] kSigning = sign(TERMINATOR,kService,SigningAlgorithm.HmacSHA256)
    Array[Byte] signature = sign(stringToSign.getBytes(),kSigning,SigningAlgorithm.HmacSHA256)
    new HeaderSigningResult(dateTime,scope,kSigning,signature)
  }
  protected def getDateTimeStamp(date:Date):String = {
    SimpleDateFormat dateTimeFormat
    dateTimeFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'")
    dateTimeFormat.setTimeZone(new SimpleTimeZone(0,"UTC"))
    dateTimeFormat.format(date)
  }
  protected def getDateStamp(date:Date):String = {
    SimpleDateFormat dateStampFormat
    dateStampFormat = new SimpleDateFormat("yyyyMMdd")
    dateStampFormat.setTimeZone(new SimpleTimeZone(0,"UTC"))
    dateStampFormat.format(date)
  }
  protected def getDateFromRequest(request:Request[_]):Date = {
    Date date = getSignatureDate(request.getTimeOffset())
    if (overriddenDate != null) date = overriddenDate
    date
  }
  protected def addHostHeader(request:Request[_]) {
    var hostHeader:String = request.getEndpoint().getHost()
    if (HttpUtils.isUsingNonDefaultPort(request.getEndpoint())) {
      hostHeader += ":" + request.getEndpoint().getPort()
    }
    request.addHeader("Host",hostHeader)
  }
  protected def getScope(request:Request[_],date:Date):String = {
    var regionName:String = extractRegionName(request.getEndpoint())
    var serviceName:String = extractServiceName(request.getEndpoint())
    var dateStamp:String = getDateStamp(date)
    var scope:String =  dateStamp + "/" + regionName + "/" + serviceName + "/" + TERMINATOR
    scope
  }
  protected def calculateContentHash(request:Request[_]):String = {
    var payloadStream:InputStream = getBinaryRequestPayloadStream(request)
    payloadStream.mark(-1)
    contentSha256 = BinaryUtils.toHex(hash(payloadStream))
    try {
      payloadStream.reset()
    } catch (IOException e) {
      throw new AmazonClientException("Unable to reset stream after calculating AWS4 signature",e)
    }
    contentSha256
  }
  protected def processRequestPayload(request:Request[_],headerSigningResult:HeaderSigningResult) { }

  protected class HeaderSigningResult {

    private var dateTime:String
    private var scope:String
    private var kSigning:Array[Byte]
    private var signature:Array[Byte]

    def HeaderSigningResult(dateTime:String,scope:String,kSigning:Array[Byte],signature:Array[Byte]) {
      this.dateTime = dateTime
      this.scope = scope
      this.kSigning = kSigning
      this.signature = signature
    }
    def getDateTime:String = dateTime
    def getScope:String = scope
    def getKSigning:Array[Byte] = {
      val kSigningCopy:Array[Byte] = new byte[kSigning.length]
      System.arraycopy(kSigning,0,kSigningCopy,0,kSigning.length)
      kSigningCopy
    }
    def getSignature:Array[Byte] = {
      val signatureCopy:Array[Byte] = new byte[signature.length]
      System.arraycopy(signature,0,signatureCopy,0,signature.length)
      signatureCopy
    }
  }
}
