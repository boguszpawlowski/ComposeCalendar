package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Suppress("FunctionNaming") // Factory function
public fun SelectionState(
  initialSelection: SelectionValue,
  selectionMode: SelectionMode
): SelectionState = SelectionStateImpl(initialSelection, selectionMode)

@Stable
public interface SelectionState {
  public var selectionValue: SelectionValue
  public var selectionMode: SelectionMode

  public fun onDateSelected(date: LocalDate) {
    selectionValue = SelectionHandler.calculateNewSelection(date, selectionValue, selectionMode)
  }

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<SelectionState, Any> = listSaver(
      save = {
        listOf(it.selectionMode, SelectionValueSerializer.serialize(it.selectionValue))
      },
      restore = { restored ->
        val selectionMode = restored[0] as SelectionMode

        SelectionState(
          selectionMode = selectionMode,
          initialSelection = SelectionValueSerializer.deserialize(restored[1], selectionMode),
        )
      }
    )
  }
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

  override var selectionValue: SelectionValue
    get() = _selectionValue
    set(value) {
      SelectionValidator.validateSelection(value, selectionMode)
      _selectionValue = value
    }

  override var selectionMode: SelectionMode
    get() = _selectionMode
    set(value) {
      if (value != selectionMode) {
        _selectionValue = SelectionValue.None
        _selectionMode = value
      }
    }
}
