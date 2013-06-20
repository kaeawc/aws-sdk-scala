package com.amazonaws.services.dynamodb.actions


import com.amazonaws.services.dynamodb.models._
import com.amazonaws.auth._
import scala.concurrent._

import play.api.http.Writeable
import play.api.http.ContentTypeOf


import play.api.libs.ws._
import play.api.libs.ws.WS._
import play.api.libs.json._
import play.api.libs.json.Json._
import play.api.libs.functional.syntax._
import play.api.libs.concurrent.Execution.Implicits._
import com.amazonaws.services.dynamodb._
import com.amazonaws.services.dynamodb.models._

object PutItem extends DynamoDBOperation[PutRequest,PutItemResult] {
  def parse(status:Long,json:String):PutItemResult = {
    PutItemResult.parse(json)
  }
}
