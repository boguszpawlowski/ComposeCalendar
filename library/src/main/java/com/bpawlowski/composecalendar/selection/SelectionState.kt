package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Stable
public interface SelectionState {
  public fun isDateSelected(date: LocalDate): Boolean
  public fun onDateSelected(date: LocalDate)
}

@Stable
public class DynamicSelectionState(
  selection: List<LocalDate>,
  selectionMode: SelectionMode,
) : SelectionState {

  private var _selection by mutableStateOf(selection)
  private var _selectionMode by mutableStateOf(selectionMode)

  public var selection: List<LocalDate>
    get() = _selection
    set(value) {
      _selection = value
    }

  public var selectionMode: SelectionMode
    get() = _selectionMode
    set(value) {
      if (value != selectionMode) {
        _selection = emptyList()
        _selectionMode = value
      }
    }

  override fun isDateSelected(date: LocalDate): Boolean = selection.contains(date)

  override fun onDateSelected(date: LocalDate) {
    selection = SelectionHandler.calculateNewSelection(date, selection, selectionMode)
  }

  internal companion object {
    @Suppress("FunctionNaming")
    fun Saver(): Saver<DynamicSelectionState, Any> = listSaver(
      save = {
        listOf(it.selectionMode, it.selection.map { it.toString() })
      },
      restore = { restored ->
        DynamicSelectionState(
          selectionMode = restored[0] as SelectionMode,
          selection = (restored[1] as? List<String>)?.map { LocalDate.parse(it) }.orEmpty(),
        )
      }
    )
  }
}

@Immutable
public object EmptySelectionState : SelectionState {
  override fun isDateSelected(date: LocalDate): Boolean = false

  override fun onDateSelected(date: LocalDate): Unit = Unit
}
