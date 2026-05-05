package benchmark.sequila

import java.util.concurrent.TimeUnit


object Timer {
  case class TimerResult[T](result: T, milliseconds: Long)

  def timed[T](block: => T): TimerResult[T] = {
    val startTime = System.nanoTime()
    val result = block
    val elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)

    TimerResult(result, elapsed)
  }
}

