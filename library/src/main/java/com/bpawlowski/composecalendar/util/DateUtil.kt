package com.bpawlowski.composecalendar.util

import java.time.LocalDate
import java.time.YearMonth

internal val LocalDate.yearMonth get() = YearMonth.of(year, month)

internal fun Collection<LocalDate>.addOrRemove(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }
