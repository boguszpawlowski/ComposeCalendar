package com.bpawlowski.composecalendar.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal val LocalDate.yearMonth: YearMonth
  get() = YearMonth.of(year, month)

internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }

internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7
