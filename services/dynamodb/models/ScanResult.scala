package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ScanResult(
  ConsumedCapacity : Option[ConsumedCapacity],
  Count            : Option[Long],
  Items            : Option[Array[String]],
  LastEvaluatedKey : Option[JsObject],
  ScannedCount     : Option[Long]
)

object ScanResult extends Function5[Option[ConsumedCapacity],Option[Long],Option[Array[String]],Option[JsObject],Option[Long],ScanResult] {
  
  implicit val r = Json.reads[ScanResult]
  implicit val w = Json.writes[ScanResult]
  
}
