package com.bpawlowski.composecalendar.day

import androidx.compose.runtime.Stable
import com.bpawlowski.composecalendar.selection.SelectionState

@Stable
public data class DayState(
  private val day: Day,
  val selectionState: SelectionState,
) : Day by day
