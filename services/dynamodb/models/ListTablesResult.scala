package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ListTablesResult(
  IndexName      : Option[String],
  IndexSizeBytes : Option[Long],
  ItemCount      : Option[Long],
  KeySchema      : Option[Array[KeySchemaElement]],
  Projection     : Option[Projection]
)

object ListTablesResult extends Function5[Option[String],Option[Long],Option[Long],Option[Array[KeySchemaElement]],Option[Projection],ListTablesResult] {
  
  implicit val r = Json.reads[ListTablesResult]
  implicit val w = Json.writes[ListTablesResult]
  
}
