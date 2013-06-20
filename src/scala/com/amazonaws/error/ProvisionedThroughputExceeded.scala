package com.amazonaws.error

class ProvisionedThroughputExceeded extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message = "You exceeded your maximum allowed provisioned throughput."
  val cause = "Your request rate is too high or the request is too large. The AWS SDKs for Amazon DynamoDB automatically retry requests that receive this . So, your request is eventually successful, unless the request is too large or your retry queue is too large to finish. Reduce the frequency of requests, using Error Retries and Exponential Backoff. Or, see Specifying Read and Write Requirements for Tables for other strategies."
  val retry = true
}