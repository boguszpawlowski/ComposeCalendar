@file:Suppress("UnderscoresInNumericLiterals")

package com.bpawlowski.composecalendar.week

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.YearMonth

internal class WeekUtilTest : ShouldSpec({

  val month = YearMonth.of(2020, 9)
  val previousMonth = YearMonth.of(2020, 8)
  val nextMonth = YearMonth.of(2020, 10)

  context("Extracting weeks without adjacent months") {
    val includeAdjacentMonths = false
    should("return days only from current month") {
      val weeks = month.getWeeks(includeAdjacentMonths)
      val days = weeks.flatMap { it.days }

      days.map { it.date }.forEach {
        it.isFromMonth(month) shouldBe true
        it.isFromMonth(previousMonth) shouldBe false
        it.isFromMonth(nextMonth) shouldBe false
      }
    }

    should("return all days from the month") {
      val weeks = month.getWeeks(includeAdjacentMonths)
      val days = weeks.flatMap { it.days }.map { it.date }

      val daysLength = month.lengthOfMonth()
      val daysFromCurrentMonth = (1..daysLength).map { month.atDay(it) }

      days shouldContainExactly daysFromCurrentMonth
    }

    should("return days properly split to weeks") {
      val weeks = month.getWeeks(includeAdjacentMonths)

      weeks[0].days shouldHaveSize 6
      weeks[1].days shouldHaveSize 7
      weeks[2].days shouldHaveSize 7
      weeks[3].days shouldHaveSize 7
      weeks[4].days shouldHaveSize 3

      weeks shouldHaveSize 5
    }
  }

  context("Extracting weeks with adjacent months") {
    val includeAdjacentMonths = true
    should("return days from current and previous months") {
      val weeks = month.getWeeks(includeAdjacentMonths)
      val days = weeks.flatMap { it.days }

      days.map { it.date }.forEachIndexed { index, day ->
        when (index) {
          0 -> {
            day.isFromMonth(month) shouldBe false
            day.isFromMonth(previousMonth) shouldBe true
            day.isFromMonth(nextMonth) shouldBe false
          }
          in 1..30 -> {
            day.isFromMonth(month) shouldBe true
            day.isFromMonth(previousMonth) shouldBe false
            day.isFromMonth(nextMonth) shouldBe false
          }
          else -> {
            day.isFromMonth(month) shouldBe false
            day.isFromMonth(previousMonth) shouldBe false
            day.isFromMonth(nextMonth) shouldBe true
          }
        }
      }
    }

    should("return all days from the month") {
      val weeks = month.getWeeks(includeAdjacentMonths)
      val days = weeks.flatMap { it.days }.map { it.date }

      val daysLength = month.lengthOfMonth()
      val endOffset = 6 - month.atEndOfMonth().dayOfWeek.ordinal
      val startOffset = month.atDay(1).dayOfWeek.ordinal - 1
      val daysFromCurrentMonth = (1..daysLength).map { month.atDay(it) }
      val daysFromNextMonth = (1..endOffset).map { nextMonth.atDay(it) }
      val daysFromPreviousMonth =
        (previousMonth.lengthOfMonth() - startOffset..previousMonth.lengthOfMonth()).map {
          previousMonth.atDay(it)
        }

      days shouldContainAll daysFromCurrentMonth + daysFromNextMonth + daysFromPreviousMonth
    }

    should("return days properly split to weeks") {
      val weeks = month.getWeeks(includeAdjacentMonths)

      weeks[0].days shouldHaveSize 7
      weeks[1].days shouldHaveSize 7
      weeks[2].days shouldHaveSize 7
      weeks[3].days shouldHaveSize 7
      weeks[4].days shouldHaveSize 7

      weeks shouldHaveSize 5
    }
  }
})

private fun LocalDate.isFromMonth(yearMonth: YearMonth) =
  month == yearMonth.month && year == yearMonth.year
