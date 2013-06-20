package com.amazonaws.error

class MissingAuthenticationToken extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "Request must contain a valid (registered) AWS Access Key ID."
  val cause = "The request did not include the required x-amz-security-token. See Making HTTP Requests to Amazon DynamoDB."
  val retry = false
}