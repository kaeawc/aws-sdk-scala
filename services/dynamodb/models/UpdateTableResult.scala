package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class UpdateTableResult(
  TableDescription : Option[TableDescription]
)

object UpdateTableResult extends Function1[Option[TableDescription],UpdateTableResult] {
  
  implicit val r = Json.reads[UpdateTableResult]
  implicit val w = Json.writes[UpdateTableResult]
  
}