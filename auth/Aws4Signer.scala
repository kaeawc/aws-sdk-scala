package com.amazonaws.auth

import java.net.URI
import java.net.URLEncoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.SimpleTimeZone
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import play.api.http.ContentTypeOf
import play.api.http.Writeable
import play.api.libs.ws.WS
import scala.Array.canBuildFrom


import com.amazonaws.Aws
import com.amazonaws.ServiceAndRegion

case class Aws4Signer(credentials: AwsCredentials, service: Option[String] = None, region: Option[String] = None) extends Signer with SignerUtils {

  private val AwsCredentials(accessKeyId, secretKey, sessionToken, expirationSeconds) = credentials

  def sign(request: WS.WSRequestHolder, method: String, body: String): WS.WSRequestHolder = {

    import Aws.dates._

    println(request.url)
    val uri = URI.create(request.url)
    val host = uri.getHost
    val date = new Date
    val dateTime = dateTimeFormat format date

    var newHeaders = request.headers

    newHeaders += "content-type" -> Seq("application/x-amz-json-1.0")

    newHeaders += "Host" -> Seq(host)
    newHeaders += "X-Amz-Date" -> Seq(dateTime)

    val resourcePath = uri.getPath match {
      case "" | null => None
      case path => Some(path)
    }

    val (signedHeaders, cannonicalRequest) = createCannonicalRequest(method, resourcePath, request.queryString, newHeaders, body)

    val dateStamp = dateStampFormat format date

    val scope = dateStamp + "/" + region.get + "/dynamodb/" + TERMINATOR

    val stringToSign = createStringToSign(dateTime, cannonicalRequest, scope)

    var key = sign(dateStamp, "AWS4" + secretKey)
    key = sign(region.get, key)
    key = sign(service.get, key)
    key = sign(TERMINATOR, key)

    val authorizationHeader = ALGORITHM + " " +
      "Credential=" + accessKeyId + "/" + scope + ", " +
      "SignedHeaders=" + signedHeaders + ", " +
      "Signature=" + toHex(sign(stringToSign, key))


    newHeaders += "Authorization" -> Seq(authorizationHeader)
    newHeaders += "X-Amz-Target" -> Seq("DynamoDB_20120810.ListTables")

    println("stringToSign")
    println(stringToSign)

    request.copy(headers = newHeaders)
  }



// POST
// /

// content-type:application/x-amz-json-1.0,application/json; charset=utf-8
// host:dynamodb.us-east-1.amazonaws.com
// x-amz-date:20130619T213809Z

// content-type;host;x-amz-date
// 44eb108b08191819600fa603ba51e9e9fb5180c23bceb3074656b6e512144d87




  private[auth] def createCannonicalRequest(method: String, resourcePath: Option[String], queryString: Map[String, Seq[String]], headers: Map[String, Seq[String]], body: String): (String, java.lang.String) = {

    val sortedHeaders = headers.keys.toSeq.sorted
    val signedHeaders = sortedHeaders.map(_.toLowerCase).mkString(";")
    val cannonicalRequest =
      method + "\n" +
        /* resourcePath */
        resourcePath.map(urlEncodePath _).getOrElse("/") + "\n" +
        /* headers */
        sortedHeaders.map(k => k.toLowerCase + ":" + headers(k).mkString(" ") + "\n").mkString + "\n" +
        /* signed headers */
        signedHeaders + "\n" +
        /* payload */
        toHex(hash(body))

    (signedHeaders, cannonicalRequest)
  }

  private[auth] def createStringToSign(dateTime: String, cannonicalRequest: String, scope: String): String =
    ALGORITHM + "\n" +
      dateTime + "\n" +
      scope + "\n" +
      toHex(hash(cannonicalRequest))

  private val ALGORITHM = "AWS4-HMAC-SHA256"
  private val TERMINATOR = "aws4_request"

}
