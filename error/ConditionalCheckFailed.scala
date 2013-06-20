package com.amazonaws.error

class ConditionalCheckFailed extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message:String = "The conditional request failed."
  val cause:String = "The expected value did not match what was stored in the system."
  val retry:Boolean = false
}