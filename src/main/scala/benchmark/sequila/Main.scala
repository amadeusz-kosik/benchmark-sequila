package benchmark.sequila

import org.apache.spark.sql.SparkSession

import java.io.PrintWriter


object Main extends App {

  implicit private val spark: SparkSession =
    SparkSession.builder().appName("Sequila benchmark").getOrCreate()

  private val InputDataPathPrefix = "/mnt/data/"
  private val OutputCSVFilePath   = "/mnt/data/benchmark/sequila.csv"

  private val DatabioColumnsMapping = Map(
    "contig"    -> "key",
    "pos_start" -> "from",
    "pos_end"   -> "to"
  )

  private val resultWriter = new PrintWriter(OutputCSVFilePath)
  resultWriter.write("test_suite_name,result_ms,output_rows\n")
  resultWriter.flush()

  (TestDataSuites.synthetic ++ TestDataSuites.databio) foreach { testDataSuiteMetadata =>
    val result = {
      val database = spark.read
        .parquet(InputDataPathPrefix + testDataSuiteMetadata.databasePath)
        .withColumnsRenamed(DatabioColumnsMapping)

      val query = spark.read
        .parquet(InputDataPathPrefix + testDataSuiteMetadata.queryPath)
        .withColumnsRenamed(DatabioColumnsMapping)

      Benchmark.runBenchmark(database, query)
    }

    resultWriter.write(s"${testDataSuiteMetadata.name},${result.milliseconds},${result.result}\n")
    resultWriter.flush()
  }

  resultWriter.close()
}