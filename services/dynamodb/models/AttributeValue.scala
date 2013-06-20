package com.amazonaws.services.dynamodb.models

import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._

case class AttributeValue(
  S : Option[String] = None,
  N : Option[String] = None,
  B : Option[String] = None,
  SS : Option[String] = None,
  NS : Option[String] = None,
  BS : Option[String] = None
) {
  var kind:String = ""
}

object AttributeValue extends Function6[Option[String],Option[String],Option[String],Option[String],Option[String],Option[String],AttributeValue] {
  
  implicit val r = Json.reads[AttributeValue]
  implicit val w = Json.writes[AttributeValue]
  
  def S(value:String):AttributeValue  = AttributeValue(Option(value), None         , None         , None         , None         , None)
  def N(value:String):AttributeValue  = AttributeValue(None         , Option(value), None         , None         , None         , None)
  def B(value:String):AttributeValue  = AttributeValue(None         , None         , Option(value), None         , None         , None)
  def SS(value:String):AttributeValue = AttributeValue(None         , None         , None         , Option(value), None         , None)
  def NS(value:String):AttributeValue = AttributeValue(None         , None         , None         , None         , Option(value), None)
  def BS(value:String):AttributeValue = AttributeValue(None         , None         , None         , None         , None         , Option(value))
}