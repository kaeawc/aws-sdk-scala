package com.amazonaws.auth

import java.util.Date
import play.api.Play.current

case class Credentials(
  accessKey    : String         = current.configuration.getString("aws.accessKey").get,
  secretKey    : String         = current.configuration.getString("aws.secretKey").get,
  sessionToken : Option[String] = None,
  expiration   : Option[Date]   = None
)