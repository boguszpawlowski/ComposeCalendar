package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import com.bpawlowski.composecalendar.selection.DaySelectionState
import java.time.LocalDate

@Immutable
public data class Day(
  val date: LocalDate,
  val selectionState: DaySelectionState = DaySelectionState.None,
  val isCurrentDay: Boolean = false,
  val isFromCurrentMonth: Boolean = false,
)
