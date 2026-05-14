val DefaultScalacOptions = Seq("-deprecation", "-unchecked", "-Xlint", "-Xdisable-assertions")

val ScalaTestVersion = "3.2.20"
val SequilaVersion = "1.3.6"
// Sequila has support for Spark up to 3.4.3
val SequilaSparkVersion = "3.4.1"

// Deduplication (assemblyMergeStrategy) for sbt-assembly
val SparkJobAssemblyMergeStrategy: String => sbtassembly.MergeStrategy = {
  // Do not erase log4j files
  case "plugin.properties" | "log4j.properties" =>
    MergeStrategy.concat

  // Otherwise it will fail with "Failed to find the data source: parquet."
  case PathList("META-INF", "services",  _*) =>
    MergeStrategy.concat

  case PathList("META-INF", xs @ _*) =>
    MergeStrategy.discard

  case x =>
    MergeStrategy.first
}


lazy val root = (project in file("."))
  .settings(name := "benchmark-sequila")


ThisBuild / scalacOptions ++= DefaultScalacOptions
ThisBuild / scalaVersion := "2.12.21"

ThisBuild / Test / parallelExecution := false

ThisBuild / assembly / assemblyJarName := "benchmark-sequila.jar"
ThisBuild / assembly / mainClass := Some("benchmark.sequila.Main")
ThisBuild / assembly / assemblyMergeStrategy := SparkJobAssemblyMergeStrategy

ThisBuild / libraryDependencies += "org.biodatageeks" %% "sequila"                % SequilaVersion
ThisBuild / libraryDependencies += "org.apache.spark" %% "spark-sql"              % SequilaSparkVersion
ThisBuild / libraryDependencies += "me.kosik"         %% "library-spark-test-17"  % s"${SequilaSparkVersion}-1.0.3"   % Test
