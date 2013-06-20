package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class DeleteRequest(
  Key : JsObject
)

object DeleteRequest extends Function1[JsObject,DeleteRequest] {
  
  implicit val r = Json.reads[DeleteRequest]
  implicit val w = Json.writes[DeleteRequest]
  
}