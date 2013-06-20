package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class BatchGetItemResult(
  ConsumedCapacity : Option[JsArray],
  Responses        : Option[JsObject],
  UnprocessedKeys  : Option[JsObject]
)

object BatchGetItemResult extends Function3[Option[JsArray],Option[JsObject],Option[JsObject],BatchGetItemResult] {
  
  implicit val r = Json.reads[BatchGetItemResult]
  implicit val w = Json.writes[BatchGetItemResult]
  
}