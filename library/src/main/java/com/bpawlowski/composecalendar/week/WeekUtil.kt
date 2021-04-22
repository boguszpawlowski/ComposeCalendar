package com.bpawlowski.composecalendar.week

import com.bpawlowski.composecalendar.day.Day
import java.time.LocalDate
import java.time.YearMonth

fun YearMonth.getWeeks(): List<Week> {
  val daysLength = lengthOfMonth()

  val dayOffset = atDay(1).dayOfWeek.ordinal

  return (1 - dayOffset..daysLength).chunked(7).mapIndexed { index, list ->
    if (index == 0) {
      list.drop(dayOffset)
    } else {
      list
    }
  }.mapIndexed { index, days ->
    Week(
      isFirstWeekOfTheMonth = index == 0,
      days = days.map { dayOfMonth ->
        val date = atDay(dayOfMonth)
        Day(
          date = date,
          isCurrentDay = date.equals(LocalDate.now()),
        )
      }
    )
  }
}
