package benchmark.sequila

import benchmark.sequila.Timer.TimerResult
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{DataFrame, SequilaSession, SparkSession}
import org.apache.spark.sql.{functions => F}
import org.slf4j.LoggerFactory

import scala.util.Try


object Benchmark {
  private lazy val logger = LoggerFactory.getLogger(getClass)

  def runBenchmark(database: DataFrame, query: DataFrame)(implicit spark: SparkSession): TimerResult[DataFrame] = Timer.timed {
    SequilaSession(spark)

    database
      .withColumn("from", F.col("from").cast(IntegerType))
      .withColumn("to",   F.col("to").  cast(IntegerType))
      .createTempView("database")

    query
      .withColumn("from", F.col("from").cast(IntegerType))
      .withColumn("to",   F.col("to").  cast(IntegerType))
      .createTempView("query")

    val joined = spark
      .sql("SELECT DISTINCT * FROM database JOIN query ON database.key = query.key AND database.to >= query.from AND database.from <= query.to")

    joined.foreach(_ => ())

    Try(spark.catalog.dropTempView("database"))
    Try(spark.catalog.dropTempView("query"))

    joined
  }
}
