package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class AttributeDefinition(
  AttributeName : String,
  AttributeType : String
)

object AttributeDefinition extends Function2[String,String,AttributeDefinition] {
  
  implicit val r = Json.reads[AttributeDefinition]
  implicit val w = Json.writes[AttributeDefinition]
  
}
