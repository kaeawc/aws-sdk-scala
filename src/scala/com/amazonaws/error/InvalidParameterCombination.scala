package com.amazonaws.error

class InvalidParameterCombination extends AmazonWebServicesError {
  val httpStatus:Int = 400
  val message:String = ""
  val cause:String = ""
  val retry:Boolean = false
}