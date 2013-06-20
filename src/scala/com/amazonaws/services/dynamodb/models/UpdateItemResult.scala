package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class UpdateItemResult(
  Attributes            : Option[JsObject],
  ConsumedCapacity      : Option[ConsumedCapacity],
  ItemCollectionMetrics : Option[ItemCollectionMetrics]
)

object UpdateItemResult extends Function3[Option[JsObject],Option[ConsumedCapacity],Option[ItemCollectionMetrics],UpdateItemResult] {
  
  implicit val r = Json.reads[UpdateItemResult]
  implicit val w = Json.writes[UpdateItemResult]
  
}
