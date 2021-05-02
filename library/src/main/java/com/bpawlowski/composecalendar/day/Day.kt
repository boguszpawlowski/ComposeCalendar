package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.bpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

@Immutable
public data class Day(
  val date: LocalDate,
  val isCurrentDay: Boolean = false,
  val isFromCurrentMonth: Boolean = false,
)

@Stable
public data class DayState(
  val day: Day,
  val selectionState: SelectionState,
)
