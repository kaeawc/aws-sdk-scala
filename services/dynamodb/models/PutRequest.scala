package com.amazonaws.services.dynamodb.models

import com.amazonaws.services.dynamodb._

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class PutRequest(
  TableName                   : String,
  Item                        : JsValue,
  ReturnConsumedCapacity      : Option[String] = None,
  ReturnItemCollectionMetrics : Option[String] = None,
  ReturnValues                : Option[String] = None,
  Expected                    : Option[JsValue] = None
) extends DynamoDBRequest {

  def operation = "PutItem"

  def toJson:JsValue = PutRequest.toJson(this)

}

object PutRequest extends ((String, JsValue, Option[String], Option[String], Option[String], Option[JsValue]) => PutRequest) {

  implicit val r = Json.reads[PutRequest]
  implicit val w = Json.writes[PutRequest]

  def toJson(request:PutRequest) = {
    Json.toJson(request)
  }

}
