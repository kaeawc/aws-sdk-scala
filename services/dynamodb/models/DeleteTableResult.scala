package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class DeleteTableResult(
  TableDescription : Option[TableDescription]
)

object DeleteTableResult extends Function[Option[TableDescription],DeleteTableResult] {
  
  implicit val r = Json.reads[DeleteTableResult]
  implicit val w = Json.writes[DeleteTableResult]
  
}