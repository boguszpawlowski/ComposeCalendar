package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public data class Day(
  val date: LocalDate,
  val isCurrentDay: Boolean = false,
  val isFromCurrentMonth: Boolean = false,
)
