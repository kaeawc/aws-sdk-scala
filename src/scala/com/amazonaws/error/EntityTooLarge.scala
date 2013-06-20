package com.amazonaws.error

class EntityTooLarge extends AmazonWebServicesError {
  val httpStatus:Int = 413
  val message = "Request Entity Too Large."
  val cause = "Maximum request size of 1 MB exceeded."
  val retry = false
}