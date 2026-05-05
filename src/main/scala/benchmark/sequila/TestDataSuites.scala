package benchmark.sequila

object TestDataSuites {

  case class TestDataSuiteMetadata(
    name:         String,
    databasePath: String,
    queryPath:    String
  )

  def findTestSuite(dataSuiteName: String)(dataPathPrefix: String): TestDataSuiteMetadata = {
    databioSuites
      .find(_.name == dataSuiteName)
      .map(suite => suite.copy(
        databasePath = dataPathPrefix + suite.databasePath,
        queryPath = dataPathPrefix + suite.queryPath
      ))
      .getOrElse(throw new RuntimeException(s"Failed to find $dataSuiteName test data suite."))
  }


  private val databioSuites = Array(
    TestDataSuiteMetadata(
      "databio-s-36",
      "databio-8p/fBrain/",
      "databio-8p/exons/"
    )
  )
}
