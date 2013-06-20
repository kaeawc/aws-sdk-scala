package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class Condition(
  ComparisonOperator : String,
  AttributeValueList : String
)

object Condition extends Function2[String,String,Condition] {
  
  implicit val r = Json.reads[Condition]
  implicit val w = Json.writes[Condition]
  
}