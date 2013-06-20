package com.amazonaws

import play.api.http.Writeable
import play.api.http.ContentTypeOf

import scala.concurrent._

import play.api.libs.iteratee.Iteratee

import play.api.libs.ws.WS
import play.api.libs.ws.Response
import play.api.libs.ws.ResponseHeaders
import play.api.libs.ws.SignatureCalculator

import java.text.SimpleDateFormat

import java.util.SimpleTimeZone
import java.util.Locale

import com.amazonaws.auth.Aws4Signer
import com.amazonaws.auth.AwsCredentials
import com.amazonaws.auth.Signer

import play.api.Play.current
/**
 * Amazon Web Services
 */
object Aws {

  val domain = "amazonaws.com"
  val service = "DynamoDB"
  val region = current.configuration.getString("aws.region").getOrElse(regions.US)
  val endpoint = "http://dynamodb." + region + "." + domain

  object EnableRedirects extends SignatureCalculator {
    def sign(request:WS.WSRequest) = request setFollowRedirects true
  }

  def withSigner4: AwsRequestBuilder =
    withSigner(Aws4Signer(AwsCredentials.fromConfiguration, Option("DynamoDB"), current.configuration.getString("aws.region")))

  def withSigner(signer: Signer) = AwsRequestBuilder(signer)

  case class AwsRequestBuilder(signer: Signer) {
    def url(url: String): AwsRequestHolder = AwsRequestHolder(WS.url(url).sign(EnableRedirects), signer)
  }

  case class AwsRequestHolder(wrappedRequest: WS.WSRequestHolder, signer: Signer) {
    def headers = wrappedRequest.headers
    def queryString = wrappedRequest.queryString

    def withHeaders(headers: (String, String)*): AwsRequestHolder =
      this.copy(wrappedRequest = wrappedRequest.withHeaders(headers: _*))

    def withQueryString(parameters: (String, String)*): AwsRequestHolder =
      this.copy(wrappedRequest = wrappedRequest.withQueryString(parameters: _*))

    def sign(method: String, body: String) =
      signer.sign(wrappedRequest, method, body)

    def post(body: String): Future[Response] = 
      sign("POST", body).post(body)

  }

  object dates {
    lazy val timeZone = new SimpleTimeZone(0, "UTC")

    def dateFormat(format: String, locale:Locale = Locale.getDefault): SimpleDateFormat = {
      val df = new SimpleDateFormat(format, locale)
      df setTimeZone timeZone
      df
    }

    lazy val dateTimeFormat = dateFormat("yyyyMMdd'T'HHmmss'Z'")
    lazy val dateStampFormat = dateFormat("yyyyMMdd")
                        
    lazy val iso8601DateFormat = dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    lazy val rfc822DateFormat = dateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

  }
}