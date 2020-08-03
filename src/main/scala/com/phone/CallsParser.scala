package com.phone

import com.phone.SparkFunctions.callCostUdf
import com.phone.model.CallRecord
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.WindowSpec
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * @author Yuriy Tumakha
 */
object CallsParser extends Logging {

  def apply(path: String)(implicit spark: SparkSession): CallsParser =
    newCallsParser(spark.sparkContext.textFile(path))

  def apply(lines: Seq[String])(implicit spark: SparkSession): CallsParser =
    newCallsParser(spark.sparkContext.parallelize(lines))

  private def newCallsParser(callsLog: RDD[String])(implicit spark: SparkSession): CallsParser = {
    import spark.implicits._
    new CallsParser(callsLog.flatMap(CallRecord(_)).toDS())
  }

}

class CallsParser(callRecords: Dataset[CallRecord])(implicit spark: SparkSession) {

  import org.apache.spark.sql.expressions.Window
  import org.apache.spark.sql.functions._
  import spark.implicits._

  spark.udf.register("callCost", callCostUdf)
  callRecords.createTempView("calls")

  val windowSpec: WindowSpec = Window.partitionBy("customerId").orderBy(col("costPerNumber").desc)

  private val callsWithCost: Dataset[(String, BigDecimal)] = spark
    .sql(""" SELECT customerId, phoneNumber,
        SUM(CAST(callCost(durationSeconds) AS DECIMAL(8,2))) AS costPerNumber
        FROM calls
        GROUP BY customerId, phoneNumber """)
    .withColumn("windowRow", row_number().over(windowSpec))
    .where(expr("windowRow != 1"))
    .groupBy("customerId")
    .sum("costPerNumber")
    .withColumnRenamed("sum(costPerNumber)", "totalCost")
    .as[(String, BigDecimal)]
    .cache()

  lazy val costPerCustomer: Map[String, BigDecimal] = callsWithCost.collect().toMap

  private val printCustomers = 1000

  def printCostPerCustomer() {
    callsWithCost.show(printCustomers)
  }

}
