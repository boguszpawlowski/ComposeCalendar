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
  initialSelection: List<LocalDate>,
  selectionMode: SelectionMode
): SelectionState = SelectionStateImpl(initialSelection, selectionMode)

@Stable
public interface SelectionState {
  public var selection: List<LocalDate>
  public var selectionMode: SelectionMode

  public fun onDateSelected(date: LocalDate) {
    selection = SelectionHandler.calculateNewSelection(date, selection, selectionMode)
  }

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<SelectionState, Any> = listSaver(
      save = {
        listOf(it.selectionMode, it.selection.map { it.toString() })
      },
      restore = { restored ->
        SelectionState(
          selectionMode = restored[0] as SelectionMode,
          initialSelection = (restored[1] as? List<String>)?.map { LocalDate.parse(it) }.orEmpty(),
        )
      }
    )
  }
}

@Stable
internal class SelectionStateImpl(
  initialSelection: List<LocalDate>,
  initialSelectionMode: SelectionMode,
) : SelectionState {

  private var _selection by mutableStateOf<List<LocalDate>>(initialSelection)
  private var _selectionMode by mutableStateOf<SelectionMode>(initialSelectionMode)

  override var selection: List<LocalDate>
    get() = _selection
    set(value) {
      _selection = value
    }

  override var selectionMode: SelectionMode
    get() = _selectionMode
    set(value) {
      if (value != selectionMode) {
        _selection = emptyList()
        _selectionMode = value
      }
    }
}
