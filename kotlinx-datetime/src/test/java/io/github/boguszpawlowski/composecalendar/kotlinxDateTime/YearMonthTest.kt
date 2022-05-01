package io.github.boguszpawlowski.composecalendar.kotlinxDateTime

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.data.Headers3
import io.kotest.data.Row3
import io.kotest.data.forAll
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import kotlinx.datetime.DateTimeUnit
import java.time.DateTimeException

internal class YearMonthTest : ShouldSpec({

  context("Year Month") {
    should("overflow a year if adding months") {
      val yearMonth = YearMonth.of(2002, 11)
      val expectedResult = YearMonth.of(2003, 1)

      val result = yearMonth.plus(2, DateTimeUnit.MONTH)

      result shouldBe expectedResult
    }
    should("add years if adding months") {
      val yearMonth = YearMonth.of(2002, 11)
      val expectedResult = YearMonth.of(2202, 11)

      val result = yearMonth.plus(2, DateTimeUnit.CENTURY)

      result shouldBe expectedResult
    }
    should("subtract years if subtracting months") {
      val yearMonth = YearMonth.of(2002, 1)
      val expectedResult = YearMonth.of(2001, 11)

      val result = yearMonth.minus(2, DateTimeUnit.MONTH)

      result shouldBe expectedResult
    }
    should("subtract year if subtracting months") {
      val yearMonth = YearMonth.of(2002, 11)
      val expectedResult = YearMonth.of(2000, 11)

      val result = yearMonth.minus(2, DateTimeUnit.YEAR)

      result shouldBe expectedResult
    }
    should("throw an error if months greater than 12") {
      shouldThrow<DateTimeException> {
        YearMonth.of(1222, 13)
      }
    }
    should("correctly add months") {
      forAll(
        table(
          Headers3("starting month", "months to add", "expected month"),
          Row3(1, 2, 3),
          Row3(12, 24, 12),
          Row3(11, 2, 1),
          Row3(1, 14, 3),
          Row3(6, 7, 1),
          Row3(10, 20, 6),
        )
      ) { startingMonth: Int, monthsToAdd: Int, expectedMonth: Int ->
        val yearMonth = YearMonth.of(2000, startingMonth)

        val result = yearMonth.plus(monthsToAdd, DateTimeUnit.MONTH).month.value

        result shouldBe expectedMonth
      }
    }
    should("correctly add years") {
      forAll(
        table(
          Headers3("starting month", "months to add", "expected year"),
          Row3(1, 2, 2000),
          Row3(12, 24, 2002),
          Row3(11, 2, 2001),
          Row3(1, 14, 2001),
          Row3(6, 7, 2001),
          Row3(10, 20, 2002),
        )
      ) { startingMonth: Int, monthsToAdd: Int, expectedYear: Int ->
        val yearMonth = YearMonth.of(2000, startingMonth)

        val result = yearMonth.plus(monthsToAdd, DateTimeUnit.MONTH).year

        result shouldBe expectedYear
      }
    }
    context("when decrementing") {
      should("subtract one month") {
        val yearMonth = YearMonth.of(2002, 11)
        val expectedResult = YearMonth.of(2002, 10)

        val result = yearMonth.dec()

        result shouldBe expectedResult
      }
      should("overflow the year when january") {
        val yearMonth = YearMonth.of(2002, 1)
        val expectedResult = YearMonth.of(2001, 12)

        val result = yearMonth.dec()

        result shouldBe expectedResult
      }
    }
    context("when incrementing") {
      should("add one month") {
        val yearMonth = YearMonth.of(2002, 11)
        val expectedResult = YearMonth.of(2002, 12)

        val result = yearMonth.inc()

        result shouldBe expectedResult
      }
      should("overflow the year when december") {
        val yearMonth = YearMonth.of(2002, 12)
        val expectedResult = YearMonth.of(2003, 1)

        val result = yearMonth.inc()

        result shouldBe expectedResult
      }
    }
  }
})
