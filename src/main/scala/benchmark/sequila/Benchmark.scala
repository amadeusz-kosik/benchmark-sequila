package benchmark.sequila

import benchmark.sequila.Timer.TimerResult
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{DataFrame, SequilaSession, SparkSession}
import org.apache.spark.sql.{functions => F}


import scala.util.Try


object Benchmark {

  def runBenchmark(database: DataFrame, query: DataFrame)(implicit spark: SparkSession): TimerResult[Long] = Timer.timed {
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

    val aggregateUDF = F.udf((_: Long, _: Long, _: Long, _: Long) => 1)

    val output = joined
      .select(F.coalesce(F.sum(aggregateUDF(
          F.col("database.from"),
          F.col("database.to"),
          F.col("query.from"),
          F.col("query.to")
        )), F.lit(0)).as("total_rows")
      )
      .collect()

    val outputCount = output.head.getLong(0)

    Try(spark.catalog.dropTempView("database"))
    Try(spark.catalog.dropTempView("query"))

    outputCount
  }
}
