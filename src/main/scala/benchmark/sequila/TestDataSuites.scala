package benchmark.sequila

object TestDataSuites {

  case class TestDataSuiteMetadata(
    name:         String,
    databasePath: String,
    queryPath:    String
  )

  val values = Array(
    // Synthetic - 1:1
    TestDataSuiteMetadata(
      "synthetic-flat-sparse",
      "synthetic/databaseUniformFlat.parquet/",
      "synthetic/querySparse.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-flat-dense",
      "synthetic/databaseUniformFlat.parquet/",
      "synthetic/queryDense.parquet/",
    ),
    // Synthetic - stacked
    TestDataSuiteMetadata(
      "synthetic-stacked-sparse",
      "synthetic/databaseUniformStacked.parquet/",
      "synthetic/querySparse.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-stacked-dense",
      "synthetic/databaseUniformStacked.parquet/",
      "synthetic/queryDense.parquet/",
    ),
    // Synthetic - skewed
    TestDataSuiteMetadata(
      "synthetic-skewed-sparse",
      "synthetic/databaseSkewedStacked.parquet/",
      "synthetic/querySparse.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-skewed-dense",
      "synthetic/databaseSkewedStacked.parquet/",
      "synthetic/queryDense.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-skewed-skewed",
      "synthetic/databaseSkewedStacked.parquet/",
      "synthetic/querySkewedDense.parquet/",
    ),
    // Synthetic - heavy stacked
    TestDataSuiteMetadata(
      "synthetic-heavy-sparse",
      "synthetic/databaseUniformHeavyStacked.parquet/",
      "synthetic/querySparse.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-heavy-dense",
      "synthetic/databaseUniformHeavyStacked.parquet/",
      "synthetic/queryDense.parquet/",
    ),
    TestDataSuiteMetadata(
      "synthetic-heavy-skewed",
      "synthetic/databaseUniformHeavyStacked.parquet/",
      "synthetic/querySkewedDense.parquet/",
    )
  )


  private val databioSuites = Array(
    TestDataSuiteMetadata(
      "databio-s-36",
      "databio-8p/fBrain/",
      "databio-8p/exons/"
    )
  )
}
