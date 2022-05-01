package io.github.boguszpawlowski.composecalendar.kotlinxDateTime

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.time.temporal.ChronoField.YEAR

/**
 * Kotlin implementation of `YearMonth` available in java.time, since `KotlinxDateTime` doesn't
 * include a substitute for it: https://github.com/Kotlin/kotlinx-datetime/issues/168
 *
 * For construction use factory methods 'of' provided in companion object
 */
@Suppress("DataClassPrivateConstructor")
public data class YearMonth private constructor(
  val year: Int,
  val month: Month,
) {

  /**
   * Increment by one month
   */
  public operator fun inc(): YearMonth = plus(1, DateTimeUnit.MONTH)

  /**
   * Decrement by one month
   */
  public operator fun dec(): YearMonth = minus(1, DateTimeUnit.MONTH)

  /**
   * Add specified amount of months to current date
   */
  public fun plus(value: Int, unit: DateTimeUnit.MonthBased): YearMonth =
    plus(value.toLong(), unit)

  /**
   * Subtract specified amount of months from current date
   */
  public fun minus(value: Int, unit: DateTimeUnit.MonthBased): YearMonth =
    plus(-value.toLong(), unit)

  public fun minus(value: Long, unit: DateTimeUnit.MonthBased): YearMonth =
    plus(-value, unit)

  public fun plus(value: Long, unit: DateTimeUnit.MonthBased): YearMonth {
    val monthsToAdd = value * unit.months
    if (monthsToAdd == 0L) {
      return this
    }
    val monthCount = year * 12L + (month.value - 1)
    val calcMonths = monthCount + monthsToAdd

    val newYear = YEAR.checkValidIntValue(calcMonths.floorDiv(12))
    val newMonth = calcMonths.floorMod(12) + 1
    return of(newYear, newMonth.toInt())
  }

  override fun toString(): String = toJavaYearMonth().toString()

  public companion object {
    public fun of(year: Int, month: Int): YearMonth = YearMonth(year, Month.of(month))

    public fun of(year: Int, month: Month): YearMonth = YearMonth(year, month)

    public fun parse(value: String): YearMonth =
      java.time.YearMonth.parse(value).toKotlinYearMonth()

    public fun now(): YearMonth {
      val today = LocalDate.now()

      return of(today.year, today.month.value)
    }
  }
}

private fun Long.floorMod(other: Long): Long = when (other) {
  0L -> this
  else -> this - floorDiv(other) * other
}
