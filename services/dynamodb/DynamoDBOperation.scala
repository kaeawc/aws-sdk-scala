package com.amazonaws.services.dynamodb

import com.amazonaws.services.dynamodb.models._
import com.amazonaws.auth._
import scala.concurrent._

import play.api.http.Writeable
import play.api.http.ContentTypeOf

import com.amazonaws._

import play.api.libs.ws._
import play.api.libs.ws.WS._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._
import play.api.libs.concurrent.Execution.Implicits._

abstract class DynamoDBOperation[Request<:DynamoDBRequest,Result<:DynamoDBResult] {

  protected def parse(status:Long,json:String):Result
  
  def execute(request:Request): Future[Result] = {
    val body = request.toJson.toString
    println(body)
    Aws.withSigner4.url(endpoint).post(body).map {
      response =>
      try {
        getErrors(response.status,response.body)
      } catch {
        case e:Exception => throw e
      }
    }
  }

  def executeBoom(request:Request): Future[Result] = {
    WS.url(endpoint).sign(signature(request)).post(request.toJson).map {
      response =>
      try {
        getErrors(response.status,response.body)
      } catch {
        case e:Exception => throw e
      }
    }
  }

  def getErrors(status:Long,json:String = "") = {
    val category = status / 100
    category match {
      case 2 => {
        if(json == "") throw new Exception("Can't parse a PutItemResult from an empty JSON string.")
        if (json contains "UnknownOperationException") throw new Exception("UnknownOperation: " + json)
        if (json contains "SerializationException") throw new Exception("SerializationException: " + json)
        if (json contains "Exception") throw new Exception("UnknownAWSException: " + json)

        status match {
          case 200 => {} // Ok
          case 201 => {} // Created
          case 202 => {} // Accepted
          case 203 => {} // NonAuthoritativeInformation
          case 204 => {} // NoContent
          case 205 => {} // ResetContent
          case 206 => {} // PartialContent
          case 207 => {} // MultiStatus
          case 301 => {} // MovedPermanently
          case 302 => {} // Found
          case 303 => {} // SeeOther
          case 304 => {} // NotModified
          case 307 => {} // TemporaryRedirect
        }

        parse(status, json)
      }
      case 3 => {
        status match {
          case 301 => {} // MovedPermanently
          case 302 => {} // Found
          case 303 => {} // SeeOther
          case 304 => {} // NotModified
          case 307 => {} // TemporaryRedirect
        }

        parse(status, json)
      }
      case 4 => {
        status match {
          case 400 => throw new Exception("BadRequest: "              + json)
          case 401 => throw new Exception("Unauthorized: "            + json)
          case 403 => throw new Exception("Forbidden: "               + json)
          case 404 => throw new Exception("NotFound: "                + json)
          case 405 => throw new Exception("MethodNotAllowed: "        + json)
          case 406 => throw new Exception("NotAcceptable: "           + json)
          case 408 => throw new Exception("RequestTimeout: "          + json)
          case 409 => throw new Exception("Conflict: "                + json)
          case 410 => throw new Exception("Gone: "                    + json)
          case 412 => throw new Exception("PreconditionFailed: "      + json)
          case 413 => throw new Exception("EntityTooLarge: "          + json)
          case 414 => throw new Exception("UriTooLong: "              + json)
          case 415 => throw new Exception("UnsupportedMediaType: "    + json)
          case 417 => throw new Exception("ExpectationFailed: "       + json)
          case 422 => throw new Exception("UnprocessableEntity: "     + json)
          case 423 => throw new Exception("Locked: "                  + json)
          case 424 => throw new Exception("FailedDependency: "        + json)
          case 429 => throw new Exception("TooManyRequest: "          + json)
          case _   => throw new Exception("Unknown Response [HTTP " + status + "]: " + json)
        }
      }
      case 5 => {
        status match {
          case 500 => throw new Exception("InternalServerError: "     + json)
          case 501 => throw new Exception("NotImplemented: "          + json)
          case 502 => throw new Exception("BadGateway: "              + json)
          case 503 => throw new Exception("ServiceUnavailable: "      + json)
          case 504 => throw new Exception("GatewayTimeout: "          + json)
          case 505 => throw new Exception("HttpVersionNotSupported: " + json)
          case 507 => throw new Exception("InsufficientStorage: "     + json)
          case _   => throw new Exception("Unknown Response [HTTP " + status + "]: " + json)
        }
      }
    }
  }
}
