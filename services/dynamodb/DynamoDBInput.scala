package com.amazonaws.services.dynamodb

import com.amazonaws.services.dynamodb.models._
import com.amazonaws.auth._

import play.api.libs.ws._
import play.api.libs.ws.WS._
import play.api.libs.json._
import play.api.libs.json.Json._
import java.util.Date

import akka.util.Timeout
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

trait DynamoDBInput {

  val table:String

  def toJson:JsValue

  def save:Future[PutItemResult] = PutItem(PutRequest(table,toJson))
  def blockingSave = {

    val timeout = Timeout(10 seconds)
    Await.result(save, timeout.duration)
  }

}