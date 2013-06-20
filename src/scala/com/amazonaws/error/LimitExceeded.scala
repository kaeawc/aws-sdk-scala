package com.amazonaws.error

class LimitExceeded extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "Too many operations for a given subscriber."
  val cause = "The number of concurrent table requests (cumulative number of tables in the CREATING, DELETING or UPDATING state) exceeds the maximum allowed of 20. The total limit of tables (currently in the ACTIVE state) is 250."
  val retry = false
}