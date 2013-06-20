package com.amazonaws.error

class Throttling extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "Rate of requests exceeds the allowed throughput."
  val cause = "This can be returned by the control plane API (CreateTable, DescribeTable, etc) when they are requested too rapidly."
  val retry = true
}