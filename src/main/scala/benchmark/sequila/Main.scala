package benchmark.sequila

import org.apache.spark.sql.SparkSession


object Main extends App {

  private val sparkSession =
    SparkSession.builder()
      .master(args(0))
      .appName("Sequila benchmark")
      .getOrCreate()
}