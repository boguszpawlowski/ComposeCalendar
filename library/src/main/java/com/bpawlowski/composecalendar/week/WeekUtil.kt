package com.bpawlowski.composecalendar.week

import com.bpawlowski.composecalendar.day.WeekDay
import com.bpawlowski.composecalendar.util.daysFrom
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

// TODO make first day of the week customizable
//  https://stackoverflow.com/questions/28450720/get-date-of-first-day-of-week-based-on-localdate-now-in-java-8/28450817
private const val DaysInAWeek = 7

internal fun YearMonth.getWeeks(
  includeAdjacentMonths: Boolean,
  firstDayOfTheWeek: DayOfWeek,
): List<Week> {
  val daysLength = lengthOfMonth()

  val starOffset = atDay(1).dayOfWeek daysFrom firstDayOfTheWeek
  val endOffset =
    DaysInAWeek - (atDay(daysLength).dayOfWeek daysFrom firstDayOfTheWeek) - 1

  return (1 - starOffset..daysLength + endOffset).chunked(DaysInAWeek).mapIndexed { index, days ->
    Week(
      isFirstWeekOfTheMonth = index == 0,
      days = days.mapNotNull { dayOfMonth ->
        val (date, isFromCurrentMonth) = when (dayOfMonth) {
          in Int.MIN_VALUE..0 -> if (includeAdjacentMonths) {
            val previousMonth = this.minusMonths(1)
            previousMonth.atDay(previousMonth.lengthOfMonth() + dayOfMonth) to false
          } else {
            return@mapNotNull null
          }
          in 1..daysLength -> atDay(dayOfMonth) to true
          else -> if (includeAdjacentMonths) {
            val previousMonth = this.plusMonths(1)
            previousMonth.atDay(dayOfMonth - daysLength) to false
          } else {
            return@mapNotNull null
          }
        }

        WeekDay(
          date = date,
          isFromCurrentMonth = isFromCurrentMonth,
          isCurrentDay = date.equals(LocalDate.now()),
        )
      }
    )
  }
}
