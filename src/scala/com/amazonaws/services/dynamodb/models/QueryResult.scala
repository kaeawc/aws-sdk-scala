package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class QueryResult(
  ConsumedCapacity : Option[ConsumedCapacity],
  Count            : Option[Long],
  Items            : Option[Array[String]],
  LastEvaluatedKey : Option[JsObject]
)

object QueryResult extends Function4[Option[ConsumedCapacity],Option[Long],Option[Array[String]],Option[JsObject],QueryResult] {
  
  implicit val r = Json.reads[QueryResult]
  implicit val w = Json.writes[QueryResult]
  
}


