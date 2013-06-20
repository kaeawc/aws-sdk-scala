package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ProvisionedThroughput(
  ReadCapacityUnits  : Long,
  WriteCapacityUnits : Long
)

object ProvisionedThroughput extends Function2[Long,Long,ProvisionedThroughput] {
  
  implicit val r = Json.reads[ProvisionedThroughput]
  implicit val w = Json.writes[ProvisionedThroughput]
  
}
