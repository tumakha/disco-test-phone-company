package com.phone

import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec._
import org.scalatest.matchers._

/**
 * @author Yuriy Tumakha
 */
class CallsParserSpec extends AnyFlatSpec with should.Matchers {

  implicit val spark: SparkSession = SparkSession.builder
    .appName("CallsParser Test")
    .master("local[4]")
    .getOrCreate()

  "CallsParser" should "calculate cost per customer" in {
    val callsParser = CallsParser("src/test/resources/test-calls.log")
    callsParser.printCostPerCustomer()

    callsParser.costPerCustomer shouldBe Map("A" -> 31.38, "B" -> 30.08, "First Last" -> 9.55)
  }

}
