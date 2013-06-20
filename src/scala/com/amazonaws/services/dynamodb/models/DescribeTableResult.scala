package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class DescribeTableResult(
  Table : Option[TableDescription]
)

object DescribeTableResult extends Function[Option[TableDescription],DescribeTableResult] {
  
  implicit val r = Json.reads[DescribeTableResult]
  implicit val w = Json.writes[DescribeTableResult]
  
}