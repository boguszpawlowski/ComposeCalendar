package io.github.boguszpawlowski.composecalendar.week

import io.github.boguszpawlowski.composecalendar.selection.fillUpTo
import io.github.boguszpawlowski.composecalendar.util.daysUntil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields
import java.util.Locale

public data class Week(
  val days: List<LocalDate>,
) {

  init {
    require(days.size == DaysInAWeek)
  }

  internal constructor(firstDay: LocalDate) : this(
    listOf(firstDay).fillUpTo(firstDay.plusDays((DaysInAWeek - 1).toLong()))
  )

  public val start: LocalDate get() = days.first()

  public val end: LocalDate get() = days.last()

  public val yearMonth: YearMonth = YearMonth.of(start.year, start.month)

  public operator fun inc(): Week = plusWeeks(1)

  public operator fun dec(): Week = plusWeeks(-1)

  public operator fun compareTo(other: Week): Int = start.compareTo(other.start)

  public fun minusWeeks(value: Long): Week = plusWeeks(-value)

  public fun plusWeeks(value: Long): Week = copy(days = days.map { it.plusWeeks(value) })

  public companion object {
    public fun now(firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek): Week {
      val today = LocalDate.now()
      val offset = today.dayOfWeek.daysUntil(firstDayOfWeek)
      val firstDay = today.minusDays(offset.toLong())

      return Week(firstDay)
    }
  }
}

public fun ChronoUnit.between(first: Week, other: Week): Int =
  between(first.start, other.start).toInt()
