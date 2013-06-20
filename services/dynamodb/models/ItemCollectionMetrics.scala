package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class ItemCollectionMetrics(
  ItemCollectionKey : Option[JsObject],
  SizeEstimateRangeGB : Option[Array[Double]]
)

object ItemCollectionMetrics extends Function2[Option[JsObject],Option[Array[Double]],ItemCollectionMetrics] {
  
  implicit val r = Json.reads[ItemCollectionMetrics]
  implicit val w = Json.writes[ItemCollectionMetrics]
  
}
