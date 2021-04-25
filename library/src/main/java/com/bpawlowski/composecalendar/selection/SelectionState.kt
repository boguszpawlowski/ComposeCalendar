package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Suppress("FunctionNaming") // Factory function
public fun SelectionState(
  initialSelection: SelectionValue,
  selectionMode: SelectionMode
): SelectionState = SelectionStateImpl(initialSelection, selectionMode)

@Stable
public interface SelectionState {
  public val selectionValue: SelectionValue
  public val selectionMode: SelectionMode
  public fun onSelectionModeChanged(newSelectionMode: SelectionMode)
  public fun onSelectionChanged(newSelection: SelectionValue)
  public fun onDateSelected(date: LocalDate)
}

@Stable
internal class SelectionStateImpl(
  initialSelectionValue: SelectionValue,
  initialSelectionMode: SelectionMode,
) : SelectionState {

  private var _selectionValue by mutableStateOf<SelectionValue>(initialSelectionValue)
  private var _selectionMode by mutableStateOf<SelectionMode>(initialSelectionMode)

  init {
    SelectionValidator.validateSelection(initialSelectionValue, initialSelectionMode)
  }

  override val selectionValue: SelectionValue
    get() = _selectionValue

  override val selectionMode: SelectionMode
    get() = _selectionMode

  override fun onSelectionChanged(newSelection: SelectionValue) {
    SelectionValidator.validateSelection(newSelection, selectionMode)
    _selectionValue = newSelection
  }

  override fun onDateSelected(date: LocalDate) {
    onSelectionChanged(
      SelectionValidator.calculateNewSelection(
        date = date,
        selectionValue = selectionValue,
        selectionMode = selectionMode
      )
    )
  }

  override fun onSelectionModeChanged(newSelectionMode: SelectionMode) {
    onSelectionChanged(SelectionValue.None)
    _selectionMode = newSelectionMode
  }
}
