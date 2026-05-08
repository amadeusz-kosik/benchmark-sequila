package benchmark.sequila

import org.apache.spark.sql.{DataFrame, SparkSession, functions => F}


object TestDataGenerator {

  def consecutive(rowsCount: Int)(implicit spark: SparkSession): DataFrame =
    generate(rowsCount, 0, 0)

  def consecutive(rowsCount: Int, offset: Int)(implicit spark: SparkSession): DataFrame =
    generate(rowsCount, offset, 0)

  def consecutive(rowsCount: Int, offset: Int, rowWidth: Int)(implicit spark: SparkSession): DataFrame =
    generate(rowsCount, offset, rowWidth)


  private def generate(rowsCount: Int, offset: Int, rowWidth: Int)(implicit spark: SparkSession): DataFrame = {
    spark
      .range(0, rowsCount)
      .toDF("i")
      .select(
        (F.col("i") + F.lit(offset)).as("from"),
        (F.col("i") + F.lit(offset) + F.lit(rowWidth)).as("to")
      )
      .withColumn("key", F.lit("no_grp"))
  }
}
