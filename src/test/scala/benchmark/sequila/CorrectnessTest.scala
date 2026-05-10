package benchmark.sequila

import me.kosik.library.spark.test.WithSparkSession
import org.apache.spark.sql.SparkSession
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers


class CorrectnessTest extends AnyFunSpec with Matchers with WithSparkSession {

    describe("Sequila benchmark") {

      it("should return empty list if no intervals overlap") {
        implicit val _sparkSession: SparkSession = sparkSession

        val database = TestDataGenerator.consecutive(100)
        val query    = TestDataGenerator.consecutive(100, 200)

        val expected = 0L
        val actual = Benchmark.runBenchmark(database, query).result

        actual should equal (expected)
      }

      it("should correctly map 1:1 relation") {
        implicit val _sparkSession: SparkSession = sparkSession

        val database = TestDataGenerator.consecutive(100)
        val query    = TestDataGenerator.consecutive(100)

        val expected = 100L
        val actual = Benchmark.runBenchmark(database, query).result

        actual should equal (expected)
      }

      it("should correctly map 1:all relation") {
        implicit val _sparkSession: SparkSession = sparkSession

        val database = TestDataGenerator.consecutive(1, 0, 100)
        val query    = TestDataGenerator.consecutive(100)

        val expected = 100L
        val actual = Benchmark.runBenchmark(database, query).result

        actual should equal (expected)
      }

      it("should correctly map all:1 relation") {
        implicit val _sparkSession: SparkSession = sparkSession

        val database = TestDataGenerator.consecutive(100)
        val query    = TestDataGenerator.consecutive(1, 0, 100)

        val expected = 100L
        val actual = Benchmark.runBenchmark(database, query).result

        actual should equal (expected)
      }
    }
}