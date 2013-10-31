
package com.amazonaws;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import com.amazonaws.http.HttpMethodName;

trait Request[T] {

  def addHeader(name:String,value:String)
  def getHeaders:Map[String,String]
  def setHeaders(headers:Map[String,String])
  def setResourcePath(path:String)
  def getResourcePath:String
  def addParameter(name:String,value:String)
  def withParameter(name:String,value:String):Request[T]
  def getParameters:Map[String,String]
  def setParameters(parameters:Map[String,String])
  def getEndpoint:URI
  def setEndpoint(endpoint:URI)
  def getHttpMethod:HttpMethodName
  def setHttpMethod(httpMethod:HttpMethodName)
  def getContent:InputStream
  def setContent(content:InputStream)
  def getServiceName:String
  def getOriginalRequest:AmazonWebServiceRequest
  def getTimeOffset:int
  def setTimeOffset(timeOffset:int)
  def withTimeOffset(timeOffset:int):Request[T]

}
