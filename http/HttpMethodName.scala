package com.amazonaws.http;

trait HttpMethodName {

  type GET extends HttpMethodName
  type POST extends HttpMethodName
  type PUT extends HttpMethodName
  type DELETE extends HttpMethodName
  type HEAD extends HttpMethodName

}
