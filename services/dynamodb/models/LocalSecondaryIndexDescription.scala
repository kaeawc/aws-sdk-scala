package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class LocalSecondaryIndexDescription(
  AttributeName : String,
  AttributeType : String
)

object LocalSecondaryIndexDescription extends Function2[String,String,LocalSecondaryIndexDescription] {
  
  implicit val r = Json.reads[LocalSecondaryIndexDescription]
  implicit val w = Json.writes[LocalSecondaryIndexDescription]
  
}