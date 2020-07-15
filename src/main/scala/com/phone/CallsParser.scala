package com.phone

import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * @author Yuriy Tumakha
 */
object CallsParser {

  def apply(path: String)(implicit spark: SparkSession): CallsParser =
    new CallsParser(spark.read.textFile(path))

  def apply(lines: Seq[String])(implicit spark: SparkSession): CallsParser = {
    import spark.implicits._

    new CallsParser(spark.sparkContext.parallelize(lines).toDS())
  }

}

class CallsParser(callsLog: Dataset[String])(implicit spark: SparkSession) {

  def printTotalCost() {
    callsLog.printSchema()
    callsLog.show()
  }

}
