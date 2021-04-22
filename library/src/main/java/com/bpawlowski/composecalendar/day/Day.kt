package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class Day(
  val date: LocalDate,
  val isCurrentDay: Boolean = false,
  val isFromPreviousMonth: Boolean = false,
)
