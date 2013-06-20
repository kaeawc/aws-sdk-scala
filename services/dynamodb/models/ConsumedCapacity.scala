package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ConsumedCapacity(
  CapacityUnits : Long,
  TableName     : String
)

object ConsumedCapacity extends Function2[Long,String,ConsumedCapacity] {
  
  implicit val r = Json.reads[ConsumedCapacity]
  implicit val w = Json.writes[ConsumedCapacity]
  
}