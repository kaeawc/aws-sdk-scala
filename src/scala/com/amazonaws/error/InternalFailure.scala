package com.amazonaws.error

class InternalFailure extends AmazonWebServicesError {
  val httpStatus:Int = 500
  val message = "The server encountered an internal error trying to fulfill the request."
  val cause = "The server encountered an error while processing your request."
  val retry = true
}