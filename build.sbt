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
  .settings(
    name := "benchmark-sequila",
    organization := "me.kosik",
    version := "1.0.0"
  )


ThisBuild / scalacOptions ++= DefaultScalacOptions
ThisBuild / scalaVersion := "2.12.21"

ThisBuild / Test / parallelExecution := false

ThisBuild / assembly / assemblyJarName := "benchmark-sequila.jar"
ThisBuild / assembly / assemblyMergeStrategy := SparkJobAssemblyMergeStrategy

ThisBuild / libraryDependencies += "org.biodatageeks" %% "sequila"                % SequilaVersion
ThisBuild / libraryDependencies += "org.apache.spark" %% "spark-sql"              % SequilaSparkVersion
ThisBuild / libraryDependencies += "org.scalatest"    %% "scalatest"              % ScalaTestVersion      % Test

// sbt-docker configuration
enablePlugins(DockerPlugin)

docker / dockerfile := {
  val jar = assembly.value

  new Dockerfile {
    from(s"spark:3.4.3-scala2.12-java11-r-ubuntu")

    copy(jar, s"/app/benchmark-sequila.jar")

    volume("/mnt/data")
    volume("/mnt/events")

    expose(4040)

    entryPoint(
      "/opt/spark/bin/spark-submit",
      "--class",                "benchmark.sequila.Main",
      "--master",               "spark://spark-master:7077",
      "--deploy-mode",          "client",
      "--driver-memory",        "4G",
      "--executor-cores",       "4",
      "--executor-memory",      "4G",
      "--total-executor-cores", "40",
      "--conf",   "spark.eventLog.enabled=true",
      "--conf",   "spark.eventLog.dir=file:/mnt/spark-events",
      s"/app/benchmark-sequila.jar"
    )
  }
}

docker / imageNames := Seq(
  // Sets the latest tag
  ImageName(
    namespace = Some(organization.value),
    repository = name.value,
    tag = Some("latest")
  ),

  // Sets a name with a tag that contains the project version
  ImageName(
    namespace = Some(organization.value),
    repository = name.value,
    tag = Some(version.value)
  )
)

