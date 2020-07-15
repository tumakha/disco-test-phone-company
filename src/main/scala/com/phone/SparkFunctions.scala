package com.phone


import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

import scala.math.BigDecimal.RoundingMode.HALF_UP

/**
 * @author Yuriy Tumakha
 */
object SparkFunctions {

  val callCost: Int => BigDecimal = duration => toBigDecimal((duration min 180) * 0.05 + (duration - 180 max 0) * 0.03)
  val callCostUdf: UserDefinedFunction = udf(callCost)

  private def toBigDecimal(dbl: Double): BigDecimal = BigDecimal(dbl).setScale(2, HALF_UP)

}
