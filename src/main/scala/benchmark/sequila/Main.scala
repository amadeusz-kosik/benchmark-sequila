package benchmark.sequila

import org.apache.spark.sql.SparkSession

import java.io.PrintWriter
import java.nio.file.{Files, Path}


object Main extends App {

  implicit private val spark: SparkSession =
    SparkSession.builder().appName("Sequila benchmark").getOrCreate()

  private val InputDataPathPrefix = "/mnt/data/"
  private val OutputCSVFilePath   = "/mnt/data/benchmark/sequila.csv"

  private val resultWriter = new PrintWriter(OutputCSVFilePath)

  if(! Files.exists(Path.of(OutputCSVFilePath)))
    resultWriter.write("test_suite_name,result_ms,output_rows")

  TestDataSuites.values foreach { testDataSuiteMetadata =>
    val result = {
      val database = spark.read.parquet(InputDataPathPrefix + testDataSuiteMetadata.databasePath)
      val query    = spark.read.parquet(InputDataPathPrefix + testDataSuiteMetadata.queryPath)

      Benchmark.runBenchmark(database, query)
    }

    resultWriter.write(s"${testDataSuiteMetadata.name},${result.milliseconds},${result.result}")
    resultWriter.flush()
  }

  resultWriter.close()
}