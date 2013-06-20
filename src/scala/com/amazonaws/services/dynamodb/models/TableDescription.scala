package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._
import java.util.Date

case class TableDescription(
  AttributeDefinitions  : Option[Array[AttributeDefinition]],
  CreationDateTime      : Option[Date],
  ItemCount             : Option[Long],
  KeySchema             : Option[Array[KeySchemaElement]],
  LocalSecondaryIndexes : Option[Array[LocalSecondaryIndexDescription]],
  ProvisionedThroughput : Option[Array[ProvisionedThroughputDescription]],
  TableName             : Option[String],
  TableSizeBytes        : Option[Long],
  TableStatus           : Option[String]
)

object TableDescription extends Function9[
  Option[Array[AttributeDefinition]],
  Option[Date],
  Option[Long],
  Option[Array[KeySchemaElement]],
  Option[Array[LocalSecondaryIndexDescription]],
  Option[Array[ProvisionedThroughputDescription]],
  Option[String],
  Option[Long],
  Option[String],
  TableDescription] {
  
  implicit val r = Json.reads[TableDescription]
  implicit val w = Json.writes[TableDescription]
  
}
