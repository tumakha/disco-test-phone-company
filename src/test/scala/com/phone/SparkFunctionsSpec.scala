package com.phone

import com.phone.SparkFunctions.callCost
import org.scalatest.flatspec._
import org.scalatest.matchers._

/**
 * @author Yuriy Tumakha
 */
// scalastyle:off
class SparkFunctionsSpec extends AnyFlatSpec with should.Matchers {

  "callCost" should "work" in {
    callCost(0) shouldBe 0
    callCost(1) shouldBe 0.05
    callCost(33) shouldBe 1.65
    callCost(180) shouldBe 9
    callCost(181) shouldBe 9.03
    callCost(600) shouldBe 21.60
  }

}
