package com.amazonaws.error

trait AmazonWebServicesError extends Exception {
  val httpStatus:Int
  val message:String
  val cause:String
  val retry:Boolean
}
