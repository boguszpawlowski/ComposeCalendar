package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.None
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.util.addOrRemove
import java.time.LocalDate

@Composable
public fun rememberSelectionState(
  initialSelection: SelectionValue,
  selectionMode: SelectionMode
): SelectionState =
  remember { SelectionStateImpl(initialSelection, selectionMode) }

@Stable
public interface SelectionState {
  public val selectionValue: SelectionValue
  public fun onSelectionChanged(newSelection: SelectionValue)
  public fun onDateSelected(date: LocalDate)
}

@Stable
public class SelectionStateImpl(
  initialSelectionValue: SelectionValue,
  selectionMode: SelectionMode,
) : SelectionState {

  private val selectionValidator: SelectionValidator = SelectionValidator(selectionMode)

  private var _selectionValue by mutableStateOf<SelectionValue>(initialSelectionValue)

  init {
    selectionValidator.validateSelection(initialSelectionValue)
  }

  override val selectionValue: SelectionValue
    get() = _selectionValue

  override fun onSelectionChanged(newSelection: SelectionValue) {
    selectionValidator.validateSelection(newSelection)
    _selectionValue = newSelection
  }

  override fun onDateSelected(date: LocalDate) {
    onSelectionChanged(selectionValidator.calculateNewSelection(date, selectionValue))
  }
}
