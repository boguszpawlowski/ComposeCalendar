package com.bpawlowski.composecalendar.util

import java.time.LocalDate
import java.time.YearMonth

internal fun LocalDate.yearMonth() = YearMonth.of(year, month)

internal fun Collection<LocalDate>.addOrRemove(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }
