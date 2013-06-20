package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class LocalSecondaryIndex(
  AttributeName : String,
  AttributeType : String
)

object LocalSecondaryIndex extends Function2[String,String,LocalSecondaryIndex] {
  
  implicit val r = Json.reads[LocalSecondaryIndex]
  implicit val w = Json.writes[LocalSecondaryIndex]
  
}