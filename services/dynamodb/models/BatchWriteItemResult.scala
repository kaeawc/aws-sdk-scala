package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class BatchWriteItemResult(
  ConsumedCapacity : Option[JsArray],
  ItemCollectionMetrics : Option[JsObject],
  UnprocessedItems : Option[JsObject]
)

object BatchWriteItemResult extends Function3[Option[JsArray],Option[JsObject],Option[JsObject],BatchWriteItemResult] {
  
  implicit val r = Json.reads[BatchWriteItemResult]
  implicit val w = Json.writes[BatchWriteItemResult]
  
}
