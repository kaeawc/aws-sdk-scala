package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class KeySchemaElement(
  AttributeName : String,
  KeyType       : String
)

object KeySchemaElement extends Function2[String,String,KeySchemaElement] {
  
  implicit val r = Json.reads[KeySchemaElement]
  implicit val w = Json.writes[KeySchemaElement]
  
}