package com.amazonaws.error

class AccessDenied extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message:String = "The request signature does not conform to AWS standards."
  val cause:String = "General authentication failure. The client did not correctly sign the request. Consult the signing documentation."
  val retry:Boolean = false
}