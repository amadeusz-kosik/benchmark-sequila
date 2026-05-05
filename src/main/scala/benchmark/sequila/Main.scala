package benchmark.sequila

import org.apache.spark.sql.SparkSession

import java.io.PrintWriter
import java.nio.file.{Files, Path}


object Main extends App {

  implicit private val spark: SparkSession =
    SparkSession.builder().appName("Sequila benchmark").getOrCreate()

  private val Array(dataPathPrefix, testSuiteName, outputCSVFilePath) = args

  private val result = {
    val testSuite = TestDataSuites.findTestSuite(testSuiteName)(dataPathPrefix)

    val database = spark.read.parquet(testSuite.databasePath)
    val query = spark.read.parquet(testSuite.queryPath)

    Benchmark.runBenchmark(database, query)
  }

  {
    val resultWriter = new PrintWriter(outputCSVFilePath)

    if(! Files.exists(Path.of(outputCSVFilePath)))
      resultWriter.write("test_suite_name,result_ms")

    resultWriter.write(s"$testSuiteName,${result.milliseconds}")
    resultWriter.flush()
    resultWriter.close()
  }
}