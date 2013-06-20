package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._
import java.util.Date

case class ProvisionedThroughputDescription(
  LastDecreaseDateTime   : Option[Date],
  LastIncreaseDateTime   : Option[Date],
  NumberOfDecreasesToday : Option[Long],
  ReadCapacityUnits      : Option[Long],
  WriteCapacityUnits     : Option[Long]
)

object ProvisionedThroughputDescription extends Function5[Option[Date],Option[Date],Option[Long],Option[Long],Option[Long],ProvisionedThroughputDescription] {
  
  implicit val r = Json.reads[ProvisionedThroughputDescription]
  implicit val w = Json.writes[ProvisionedThroughputDescription]
  
}