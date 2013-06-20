package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class AttributeValueUpdate(
  Action : String,
  Value : String
)

object AttributeValueUpdate extends Function2[String,String,AttributeValueUpdate] {
  
  implicit val r = Json.reads[AttributeValueUpdate]
  implicit val w = Json.writes[AttributeValueUpdate]
  
}