package com.amazonaws.error

class ResourceNotFound extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "The resource which is being requested does not exist."
  val cause = "Table which is being requested does not exist, or is too early in the CREATING state."
  val retry = false
}
