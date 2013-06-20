package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class DeleteItemResult(
  Attributes            : Option[JsObject],
  ConsumedCapacity      : Option[ConsumedCapacity],
  ItemCollectionMetrics : Option[ItemCollectionMetrics]
)

object DeleteItemResult extends Function3[Option[JsObject],Option[ConsumedCapacity],Option[ItemCollectionMetrics],DeleteItemResult] {
  
  implicit val r = Json.reads[DeleteItemResult]
  implicit val w = Json.writes[DeleteItemResult]
  
}
