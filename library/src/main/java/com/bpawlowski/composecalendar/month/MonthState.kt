package com.bpawlowski.composecalendar.month

import androidx.compose.runtime.Immutable
import java.time.YearMonth

@Immutable
data class MonthState(
  val month: YearMonth,
)
