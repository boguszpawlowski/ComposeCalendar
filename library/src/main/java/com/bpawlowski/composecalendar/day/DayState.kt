package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class DayState(
  val date: LocalDate,
)
