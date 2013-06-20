package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class Projection(
  NonKeyAttributes : Option[Array[String]],
  ProjectionType   : Option[String]
)

object Projection extends Function2[Option[Array[String]],Option[String],Projection] {
  
  implicit val r = Json.reads[Projection]
  implicit val w = Json.writes[Projection]
  
}
