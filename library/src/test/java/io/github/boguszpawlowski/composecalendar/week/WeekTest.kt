package io.github.boguszpawlowski.composecalendar.week

import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

internal class WeekTest : ShouldSpec({

  val today = LocalDate.of(2000, 10, 2)

  context("Secondary constructor") {
    val week = Week(firstDay = today)

    should("properly create an instance of week") {
      week.asClue {
        it.days.first() shouldBe today
        it.days.last() shouldBe today.plusDays(6)
        it.days.size shouldBe 7
      }
    }
  }

  context("Incrementing") {
    val week = Week(firstDay = today).inc()

    should("shift the days by 7") {
      week.asClue {
        it.days.first() shouldBe today.plusWeeks(1)
        it.days.last() shouldBe today.plusDays(6).plusWeeks(1)
        it.days.size shouldBe 7
      }
    }
  }

  context("Decrementing") {
    val week = Week(firstDay = today).dec()

    should("shift the days by -7") {
      week.asClue {
        it.days.first() shouldBe today.plusWeeks(-1)
        it.days.last() shouldBe today.plusDays(6).plusWeeks(-1)
        it.days.size shouldBe 7
      }
    }
  }

  context("Adding a week") {
    val week = Week(firstDay = today).plusWeeks(7)

    should("add correct number of days") {
      week.asClue {
        it.days.first() shouldBe today.plusWeeks(7)
        it.days.last() shouldBe today.plusDays(6).plusWeeks(7)
        it.days.size shouldBe 7
      }
    }
  }

  context("Constructing a week with invalid number of days") {
    should("throw") {
      shouldThrow<IllegalArgumentException> {
        Week(List(18) { LocalDate.now() })
      }
    }
  }
})
