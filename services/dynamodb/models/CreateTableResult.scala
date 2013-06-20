package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class CreateTableResult(
  TableDescription : TableDescription
)

object CreateTableResult extends Function1[TableDescription,CreateTableResult] {
  
  implicit val r = Json.reads[CreateTableResult]
  implicit val w = Json.writes[CreateTableResult]
  
}