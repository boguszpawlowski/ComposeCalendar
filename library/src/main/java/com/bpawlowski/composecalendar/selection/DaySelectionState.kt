package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable

@Immutable
sealed interface DaySelectionState {
  val isSelected: Boolean

  object None : DaySelectionState {
    override val isSelected = false
  }

  data class Single(override val isSelected: Boolean) : DaySelectionState
  data class Period(
    override val isSelected: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
  ) : DaySelectionState
}
