package com.phone.model

import java.time.LocalTime

import org.apache.spark.internal.Logging

import scala.util.Try

/**
 * @author Yuriy Tumakha
 */
object CallRecord extends Logging {

  def apply(line: String): Option[CallRecord] =
    line.split(" ") match {
      case values if values.size >= 3 =>
        val n = values.size
        Try {
          val customerId = values.dropRight(2).mkString(" ")
          val phoneNumber = values(n - 2).filter(_.isDigit).toLong
          val durationSeconds = LocalTime.parse(values(n - 1)).toSecondOfDay
          Some(CallRecord(customerId, phoneNumber, durationSeconds))
        }.recover {
          case ex: Throwable =>
            log.error(s"Rejected log line [$line]", ex)
            None
        }.get
      case _ => None
    }

}

case class CallRecord(customerId: String, phoneNumber: Long, durationSeconds: Int)
