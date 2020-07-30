package com.phone.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

/**
 * @author Yuriy Tumakha
 */
class CallRecordSpec extends AnyFlatSpec with should.Matchers {

  "CallsParser" should "parseCallRecord" in {
    val lines = Seq("A 555-333-212 00:02:03", "B 777 02:56:32", "CustomerID +447774445555 00:00:45")
    lines.map(CallRecord(_)) shouldBe Seq(
      Some(CallRecord("A", 555333212, 123)),
      Some(CallRecord("B", 777, 10592)),
      Some(CallRecord("CustomerID", 447774445555L, 45))
    )
  }

  it should "parseCallRecord with space in customerId" in {
    CallRecord("First Last 555-333-212 00:01:30") shouldBe
      Some(CallRecord("First Last", 555333212, 90))
  }

  it should "parseCallRecord with None result for invalid line" in {
    CallRecord("") shouldBe None
    CallRecord("A,555-333-212,00:02:03") shouldBe None
    CallRecord("Wrong number 00:02:03") shouldBe None
    CallRecord("Wrong 555-333-212 000203") shouldBe None
  }
}
