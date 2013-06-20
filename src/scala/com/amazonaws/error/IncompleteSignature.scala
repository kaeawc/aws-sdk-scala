package com.amazonaws.error

class IncompleteSignature extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message:String = "The request signature does not conform to AWS standards."
  val cause:String = "The signature in the request did not include all of the required components. See HTTP Header Contents."
  val retry:Boolean = false
}