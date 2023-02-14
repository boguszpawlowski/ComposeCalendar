package io.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Stable
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.states.EventState

public typealias NonSelectableDayState = DayState<EmptySelectionState>

/**
 * Contains information about current selection as well as date of rendered day
 */
@Stable
public data class DayState<T : SelectionState>(
  private val day: Day,
  val selectionState: T,
  val eventState: EventState
) : Day by day
