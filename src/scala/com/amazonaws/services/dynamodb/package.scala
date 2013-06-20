package com.amazonaws.services

import play.api.Play.current
import scala.concurrent.Future
import com.amazonaws.auth._
import com.amazonaws.services.dynamodb.models._

package object dynamodb {

  val domain = "amazonaws.com"
  val service = "DynamoDB"
  val region = current.configuration.getString("aws.region").getOrElse("us-east-1")
  val endpoint = "http://dynamodb." + region + "." + domain

  private[dynamodb] def signature(request:DynamoDBRequest) = com.amazonaws.auth.signature.V4(Credentials(),service,region,request)

  // def BatchGetItem   : Future[BatchGetItemResult]   = None
  // def BatchWriteItem : Future[BatchWriteItemResult] = None
  // def CreateTable    : Future[CreateTableResult]    = None
  // def DeleteItem     : Future[DeleteItemResult]     = None
  // def DeleteTable    : Future[DeleteTableResult]    = None
  // def DescribeTable  : Future[DescribeTableResult]  = None
  def GetItem(request:GetItemRequest)     : Future[GetItemResult]        = actions.GetItem.execute(request)
  // def ListTables     : Future[ListTablesResult]     = None
  def PutItem(item:PutRequest):Future[PutItemResult]        = actions.PutItem.execute(item)
  // def Query          : Future[QueryResult]          = None
  // def Scan           : Future[ScanResult]           = None
  // def UpdateItem     : Future[UpdateItemResult]     = None
  // def UpdateTable    : Future[UpdateTableResult]    = None

}
