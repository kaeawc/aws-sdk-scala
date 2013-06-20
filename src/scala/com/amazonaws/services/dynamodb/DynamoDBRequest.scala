package com.amazonaws.services.dynamodb

import com.amazonaws.AWSRequest
import play.api.libs.json.JsValue

trait DynamoDBRequest extends AWSRequest {

  def operation : String
  def toJson    : JsValue

}