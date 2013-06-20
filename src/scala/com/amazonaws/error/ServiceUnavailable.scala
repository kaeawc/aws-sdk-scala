package com.amazonaws.error

class ServiceUnavailable extends AmazonWebServicesError {
  val httpStatus:Int = 500
  val message = "The service is currently unavailable or busy."
  val cause = "There was an unexpected error on the server while processing your request."
  val retry = true
}