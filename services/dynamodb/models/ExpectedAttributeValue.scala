package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ExpectedAttributeValue(
  Exists : Option[Boolean],
  Value  : Option[AttributeValue]
)

object ExpectedAttributeValue extends Function2[Option[Boolean],Option[AttributeValue],ExpectedAttributeValue] {
  
  implicit val r = Json.reads[ExpectedAttributeValue]
  implicit val w = Json.writes[ExpectedAttributeValue]
  
}
