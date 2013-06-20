package com.amazonaws.error

class UnrecognizedClient extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "The Access Key ID or security token is invalid."
  val cause = "The request signature is incorrect. The most likely cause is an invalid AWS access key ID or secret key."
  val retry = true
}