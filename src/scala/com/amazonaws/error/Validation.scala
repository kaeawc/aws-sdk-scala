package com.amazonaws.error

class Validation extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "One or more required parameter values were missing."
  val cause = "One or more required parameter values were missing."
  val retry = false
}