package com.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Stable
import com.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import com.github.boguszpawlowski.composecalendar.selection.SelectionState

public typealias SimpleDayState = DayState<EmptySelectionState>

@Stable
public data class DayState<T : SelectionState>(
  private val day: Day,
  val selectionState: T,
) : Day by day
