package io.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Stable
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState

public typealias SimpleDayState = DayState<EmptySelectionState>

@Stable
public data class DayState<T : SelectionState>(
  private val day: Day,
  val selectionState: T,
) : Day by day
