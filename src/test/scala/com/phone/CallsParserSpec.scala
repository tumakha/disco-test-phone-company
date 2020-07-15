package com.phone

import com.phone.CallsParser.CallRecord
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

  "CallsParser" should "parseCallRecord" in {
    val lines = Seq("A 555-333-212 00:02:03", "B 777 02:56:32", "CustomerID +447774445555 00:00:45")
    lines.map(CallsParser.parseCallRecord) shouldBe Seq(
      Some(CallRecord("A", 555333212, 123)),
      Some(CallRecord("B", 777, 10592)),
      Some(CallRecord("CustomerID", 447774445555L, 45))
    )
  }

  it should "parseCallRecord with space in customerId" in {
    CallsParser.parseCallRecord("First Last 555-333-212 00:01:30") shouldBe
      Some(CallRecord("First Last", 555333212, 90))
  }

  it should "parseCallRecord with None result for invalid line" in {
    CallsParser.parseCallRecord("") shouldBe None
    CallsParser.parseCallRecord("A,555-333-212,00:02:03") shouldBe None
    CallsParser.parseCallRecord("Wrong number 00:02:03") shouldBe None
    CallsParser.parseCallRecord("Wrong 555-333-212 000203") shouldBe None
  }

  it should "calculate cost per customer" in {
    val callsParser = CallsParser("src/test/resources/test-calls.log")
    callsParser.printCostPerCustomer()

    callsParser.costPerCustomer shouldBe Map("A" -> 31.38, "B" -> 30.08, "First Last" -> 9.55)
  }

}
