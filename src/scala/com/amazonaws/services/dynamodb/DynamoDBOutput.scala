package com.amazonaws.services.dynamodb

import com.amazonaws.services.dynamodb.models._
import com.amazonaws.auth._

import play.api.libs.ws._
import play.api.libs.ws.WS._
import play.api.libs.json._
import play.api.libs.json.Json._
import java.util.Date

import akka.util.Timeout
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

trait DynamoDBOutput {

  def table:String

  def getHash:(String,Any)

  def getKey:JsObject = {
    val (name,value) = getHash
    value match {
      case num:Long        => JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("N" -> JsString(num.toString)))))))
      case str:String      => JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("S" -> JsString(str)))))))
      // case seq:Seq[_]   => {
      //   if (seq.length <= 0)
      //     throw new Exception()

      //   JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("N" -> JsString("123")))))))
        // JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("SN" -> JsArray(JsString(num))))))))
      // }
      // case str:Seq[String] => JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("SS" -> JsArray(JsString(str))))))))
      // case bin:Array[Byte] => JsObject(Seq("Key" -> JsObject(Seq(name -> JsObject(Seq("N" -> bin.map { byt => JsArray(byt) }))))))
      case _ => throw new Exception("Cannot use this as a DynamoDB key.")
    }
  }

  def getAttributes(attributes:Map[String,String]):Option[JsArray] = None

  def getConsistentRead:Option[JsBoolean] = Option(JsBoolean(true))

  def getConsumedCapacity:Option[JsString] = None

  def getByHash(attributes:Map[String,String] = Map[String,String]()):Future[GetItemResult] = GetItem(
    GetItemRequest(
      getKey,
      JsString(table),
      getConsistentRead,
      getAttributes(attributes),
      getConsumedCapacity))

}
