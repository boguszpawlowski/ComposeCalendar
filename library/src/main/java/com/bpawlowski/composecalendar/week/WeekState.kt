package com.bpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class WeekState(
  val days: List<LocalDate>,
)
