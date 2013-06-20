package com.amazonaws.error

class ResourceInUse extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "The resource which you are attempting to change is in use."
  val cause = "You tried to recreate an existing table, or delete a table currently in the CREATING state."
  val retry = false
}
