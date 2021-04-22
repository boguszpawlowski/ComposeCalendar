package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable

@Immutable
public sealed interface DaySelectionState {
  public val isSelected: Boolean

  public object None : DaySelectionState {
    public override val isSelected: Boolean = false
  }

  public data class Single(override val isSelected: Boolean) : DaySelectionState
  public data class Period(
    override val isSelected: Boolean,
    val isFirst: Boolean,
    val isLast: Boolean,
  ) : DaySelectionState
}
