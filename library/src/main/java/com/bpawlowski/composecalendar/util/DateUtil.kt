package com.bpawlowski.composecalendar.util

import java.time.LocalDate
import java.time.YearMonth

internal fun LocalDate.yearMonth() = YearMonth.of(year, month)
