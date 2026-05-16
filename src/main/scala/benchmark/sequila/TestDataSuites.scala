package benchmark.sequila

object TestDataSuites {

  case class TestDataSuiteMetadata(
    name:         String,
    databasePath: String,
    queryPath:    String
  )

  val synthetic: Array[TestDataSuiteMetadata] = {
    val datasets = Array(
      // Synthetic - 1:1
      TestDataSuiteMetadata(
        "synthetic-flat-sparse",
        "databaseUniformFlat",
        "querySparse",
      ),
      TestDataSuiteMetadata(
        "synthetic-flat-dense",
        "databaseUniformFlat",
        "queryDense",
      ),
      TestDataSuiteMetadata(
        "synthetic-flat-dummy",
        "databaseUniformFlat",
        "queryDummy",
      ),
      // Synthetic - stacked
      TestDataSuiteMetadata(
        "synthetic-stacked-sparse",
        "databaseUniformStacked",
        "querySparse",
      ),
      TestDataSuiteMetadata(
        "synthetic-stacked-dense",
        "databaseUniformStacked",
        "queryDense",
      ),
      // Synthetic - skewed
      TestDataSuiteMetadata(
        "synthetic-skewed-sparse",
        "databaseSkewedStacked",
        "querySparse",
      ),
      TestDataSuiteMetadata(
        "synthetic-skewed-dense",
        "databaseSkewedStacked",
        "queryDense",
      ),
      TestDataSuiteMetadata(
        "synthetic-skewed-skewed",
        "databaseSkewedStacked",
        "querySkewedDense",
      ),
      // Synthetic - heavy stacked
      TestDataSuiteMetadata(
        "synthetic-heavy-sparse",
        "databaseUniformHeavyStacked",
        "querySparse",
      ),
      TestDataSuiteMetadata(
        "synthetic-heavy-dense",
        "databaseUniformHeavyStacked",
        "queryDense",
      ),
      TestDataSuiteMetadata(
        "synthetic-heavy-skewed",
        "databaseUniformHeavyStacked",
        "querySkewedDense",
      )
    )

    val sizes = Array(
      "100000",
      "1000000",
      "10000000"
    )

    for {
      size    <- sizes
      dataset <- datasets
    } yield TestDataSuiteMetadata(
      dataset.name + "-" + size,
      "synthetic/" + size + "/" + dataset.databasePath + ".parquet/",
      "synthetic/" + size + "/" + dataset.queryPath    + ".parquet/"
    )
  }


  val databio = Array(
    // -- small -- //
    TestDataSuiteMetadata(
      "databio-s-36",
      "databio-8p/fBrain/",
      "databio-8p/exons/"
    ),
    TestDataSuiteMetadata(
      "databio-s-35",
      "databio-8p/fBrain/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-s-34",
      "databio-8p/exons/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-s-33",
      "databio-8p/chainRn4/",
      "databio-8p/fBrain/"
    ),
    TestDataSuiteMetadata(
      "databio-s-32",
      "databio-8p/fBrain/",
      "databio-8p/chainVicPac2/"
    ),
    TestDataSuiteMetadata(
      "databio-s-31",
      "databio-8p/fBrain/",
      "databio-8p/chainOrnAna1/"
    ),
    TestDataSuiteMetadata(
      "databio-s-30",
      "databio-8p/fBrain/",
      "databio-8p/chainXenTro3Link/"
    ),
    // -- medium -- //
    TestDataSuiteMetadata(
      "databio-m-29",
      "databio-8p/fBrain/",
      "databio-8p/chainMonDom5Link/"
    ),
    TestDataSuiteMetadata(
      "databio-m-28",
      "databio-8p/chainRn4/",
      "databio-8p/exons/"
    ),
    TestDataSuiteMetadata(
      "databio-m-27",
      "databio-8p/chainRn4/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-m-26",
      "databio-8p/exons/",
      "databio-8p/chainOrnAna1/"
    ),
    TestDataSuiteMetadata(
      "databio-m-25",
      "databio-8p/chainOrnAna1/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-m-24",
      "databio-8p/chainVicPac2/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-m-23",
      "databio-8p/ex-rna/",
      "databio-8p/fBrain/"
    ),
    // -- large -- //
    TestDataSuiteMetadata(
      "databio-l-22",
      "databio-8p/chainVicPac2/",
      "databio-8p/exons/"
    ),
    TestDataSuiteMetadata(
      "databio-l-21",
      "databio-8p/chainXenTro3Link/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-l-20",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/ex-anno/"
    ),
    TestDataSuiteMetadata(
      "databio-l-19",
      "databio-8p/ex-rna/",
      "databio-8p/exons/"
    ),
    TestDataSuiteMetadata(
      "databio-l-18",
      "databio-8p/chainXenTro3Link/",
      "databio-8p/exons/"
    ),
    // -- x-large -- //
    TestDataSuiteMetadata(
      "databio-l-17",
      "databio-8p/chainOrnAna1/",
      "databio-8p/ex-rna/"
    ),
    TestDataSuiteMetadata(
      "databio-l-16",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/exons/"
    ),
    TestDataSuiteMetadata(
      "databio-l-15",
      "databio-8p/chainRn4/",
      "databio-8p/ex-rna/"
    ),
    TestDataSuiteMetadata(
      "databio-l-14",
      "databio-8p/chainVicPac2/",
      "databio-8p/ex-rna/"
    ),
    TestDataSuiteMetadata(
      "databio-l-13",
      "databio-8p/ex-anno/",
      "databio-8p/ex-rna/"
    ),
    // -- xx-large -- //
    TestDataSuiteMetadata(
      "databio-l-12",
      "databio-8p/chainOrnAna1/",
      "databio-8p/chainRn4/"
    ),
    TestDataSuiteMetadata(
      "databio-l-11",
      "databio-8p/chainXenTro3Link/",
      "databio-8p/ex-rna/"
    ),
    TestDataSuiteMetadata(
      "databio-l-10",
      "databio-8p/chainRn4/",
      "databio-8p/chainVicPac2/"
    ),
    TestDataSuiteMetadata(
      "databio-l-9",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/ex-rna/"
    ),
    TestDataSuiteMetadata(
      "databio-l-8",
      "databio-8p/chainOrnAna1/",
      "databio-8p/chainVicPac2/"
    ),
    TestDataSuiteMetadata(
      "databio-l-7",
      "databio-8p/chainRn4/",
      "databio-8p/chainXenTro3Link/"
    ),
    TestDataSuiteMetadata(
      "databio-l-6",
      "databio-8p/chainOrnAna1/",
      "databio-8p/chainXenTro3Link/"
    ),
    TestDataSuiteMetadata(
      "databio-l-5",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/chainRn4/"
    ),
    TestDataSuiteMetadata(
      "databio-l-4",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/chainOrnAna1/"
    ),
    TestDataSuiteMetadata(
      "databio-l-3",
      "databio-8p/chainVicPac2/",
      "databio-8p/chainXenTro3Link/"
    ),
    TestDataSuiteMetadata(
      "databio-l-2",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/chainVicPac2/"
    ),
    TestDataSuiteMetadata(
      "databio-l-1",
      "databio-8p/chainMonDom5Link/",
      "databio-8p/chainXenTro3Link/"
    )
  )
}
