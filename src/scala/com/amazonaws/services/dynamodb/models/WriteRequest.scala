package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class WriteRequest(
  DeleteRequest : Option[DeleteRequest],
  PutRequest    : Option[PutRequest]
)

object WriteRequest extends Function2[Option[DeleteRequest],Option[PutRequest],WriteRequest] {
  
  implicit val r = Json.reads[WriteRequest]
  implicit val w = Json.writes[WriteRequest]
  
}
