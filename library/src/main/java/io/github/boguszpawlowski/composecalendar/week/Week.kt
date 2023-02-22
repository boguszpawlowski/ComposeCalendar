package io.github.boguszpawlowski.composecalendar.week

import io.github.boguszpawlowski.composecalendar.month.DaysOfWeek
import io.github.boguszpawlowski.composecalendar.selection.fillUpTo
import io.github.boguszpawlowski.composecalendar.util.daysUntil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

public data class Week(
  val days: List<LocalDate>,
) {

  init {
    require(days.size == 7)
  }

  internal constructor(firstDay: LocalDate) : this(
    listOf(firstDay).fillUpTo(firstDay.plusDays((DaysOfWeek - 1).toLong()))
  )

  public val start: LocalDate get() = days.first()

  public val end: LocalDate get() = days.last()

  public operator fun inc(): Week = plusWeeks(1)

  public operator fun dec(): Week = plusWeeks(-1)

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
