package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class KeysAndAttributes(
  Keys            : String,
  AttributesToGet : Option[Array[String]],
  ConsistentRead  : Option[Boolean]
)

object KeysAndAttributes extends Function3[String,Option[Array[String]],Option[Boolean],KeysAndAttributes] {
  
  implicit val r = Json.reads[KeysAndAttributes]
  implicit val w = Json.writes[KeysAndAttributes]
  
}
