package com.phone

import org.apache.spark.sql.SparkSession

import scala.io.Source

object Main extends App {

  val defaultCallsLog = "calls.log"

  implicit val spark: SparkSession = SparkSession.builder
    .appName("Calls Log Parser")
    .master("local[4]")
    .getOrCreate()

  val callsParser = args match {
    case Array(path) =>
      println(s"Read $path")
      CallsParser(path)
    case _ =>
      println(s"Read $defaultCallsLog from classpath")
      val lines = Source.fromResource(defaultCallsLog).getLines().toSeq
      CallsParser(lines)
  }

  callsParser.printTotalCost()

}
