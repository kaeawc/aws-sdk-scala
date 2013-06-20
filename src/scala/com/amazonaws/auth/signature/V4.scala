package com.amazonaws.auth.signature

import com.amazonaws.auth._
import com.amazonaws._
import play.api.libs.ws._
import play.api.libs.ws.WS._


import java.net.URI
import java.net.URLEncoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SimpleTimeZone
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac

case class V4(
  credentials : Credentials,
  service : String,
  region : String,
  body : AWSRequest
) extends Signature {

  def sign(request: WSRequest) {

    // val dateStamp = "20130607T145800Z"

    // val fakeHeaders = Map(
    //   "Host"                 -> Seq("dynamodb.us-east-1.amazonaws.com"),
    //   "X-Amz-Date"           -> Seq(dateStamp),
    //   "Authorization"        -> Seq(
    //     "AWS4-HMAC-SHA256 Credential=" + credentials.accessKey + "/" + dateStamp + "/" + region + "/" + service + "/" + TERMINATOR,
    //     "SignedHeaders=content-length;content-type;host;user-agent;x-amz-content-sha256;x-amz-date;x-amz-target",
    //     "Signature=" + getSignatureKey(credentials.accessKey, dateStamp, region, service)),
    //   // "User-Agent"           -> Seq("SimpleAmazonDynamoDB/1.0"),
    //   // "x-amz-content-sha256" -> Seq(toHex(HmacSHA256(stringToSign, key))),
    //   "Content-Type"         -> Seq("application/x-amz-json-1.0"),
    //   "Content-Length"       -> Seq("0"),
    //   "Connection"           -> Seq("Keep-Alive"),
    //   "X-Amz-Target"         -> Seq("DynamoDB_20120810.PutItem ")
    //   )

    // request.setHeaders(fakeHeaders)
    // request.setQueryString(queryString: Map[String, Seq[String]])
  }

  def HmacSHA256(data:String,key:Array[Byte]):Array[Byte] = {
       val algorithm="HmacSHA256"
       var mac = Mac.getInstance(algorithm)
       mac.init(new SecretKeySpec(key, algorithm))
       return mac.doFinal(data.getBytes("UTF8"))
  }

  def getSignatureKey(key:String, dateStamp:String, regionName:String, serviceName:String):Array[Byte] = {
       val kSecret = ("AWS4" + key).getBytes("UTF8")
       val kDate    = HmacSHA256(dateStamp, kSecret)
       val kRegion  = HmacSHA256(regionName, kDate)
       val kService = HmacSHA256(serviceName, kRegion)
       val kSigning = HmacSHA256("aws4_request", kService)
       return kSigning
  }

  private val ALGORITHM = "AWS4-HMAC-SHA256"
  private val TERMINATOR = "aws4_request"
}