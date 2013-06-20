package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import com.amazonaws.services.dynamodb._

case class GetItemResult(
  ConsumedCapacity : Option[ConsumedCapacity] = None,
  Item             : Option[JsObject] = None
) extends DynamoDBResult {
  def toJson:JsValue = GetItemResult.toJson(this)
}

object GetItemResult extends ((Option[ConsumedCapacity],Option[JsObject]) => GetItemResult) {
  
  implicit val r = Json.reads[GetItemResult]
  implicit val w = Json.writes[GetItemResult]

  def parse(json:String):GetItemResult = {
    Json.fromJson(Json.parse(json)).get
  }

  def toJson(result:GetItemResult):JsValue = {
    Json.toJson(result)
  }
}
