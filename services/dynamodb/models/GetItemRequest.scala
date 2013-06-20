package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import com.amazonaws.services.dynamodb._

case class GetItemRequest(
  Key                    : JsObject,
  TableName              : JsString,
  ConsistentRead         : Option[JsBoolean],
  AttributesToGet        : Option[JsArray],
  ReturnConsumedCapacity : Option[JsString]
) extends DynamoDBRequest {

  def operation = "GetItem"

  def toJson:JsValue = GetItemRequest.toJson(this)

}

object GetItemRequest extends ((JsObject,JsString,Option[JsBoolean],Option[JsArray],Option[JsString]) => GetItemRequest) {
  
  implicit val r = Json.reads[GetItemRequest]
  implicit val w = Json.writes[GetItemRequest]

  def toJson(request:GetItemRequest) = {
    Json.toJson(request)
  }
  
}