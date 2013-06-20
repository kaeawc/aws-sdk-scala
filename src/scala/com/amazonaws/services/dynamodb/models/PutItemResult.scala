package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._
import com.amazonaws.services.dynamodb._

case class PutItemResult(
  Attributes            : Option[JsObject]              = None,
  ConsumedCapacity      : Option[ConsumedCapacity]      = None,
  ItemCollectionMetrics : Option[ItemCollectionMetrics] = None
) extends DynamoDBResult {
  def toJson:JsValue = PutItemResult.toJson(this)
}

object PutItemResult extends Function3[
  Option[JsObject],
  Option[ConsumedCapacity],
  Option[ItemCollectionMetrics],
  PutItemResult] {

  implicit val r = Json.reads[PutItemResult]
  implicit val w = Json.writes[PutItemResult]

  def parse(json:String):PutItemResult = {
    Json.fromJson(Json.parse(json)).get
  }

  def toJson(result:PutItemResult):JsValue = {
    Json.toJson(result)
  }

}