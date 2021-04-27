package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.None
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.util.addOrRemove
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

public fun SelectionState.onDateSelected(date: LocalDate) {
  selectionValue = when (val selectionValue = selectionValue) {
    SelectionValue.None -> when (selectionMode) {
      None -> SelectionValue.None
      Single -> SelectionValue.Single(date)
      Multiple -> SelectionValue.Multiple(listOf(date))
      Period -> SelectionValue.Period(start = date)
    }
    is SelectionValue.Single -> if (selectionValue.date == date) {
      SelectionValue.None
    } else {
      SelectionValue.Single(date)
    }
    is SelectionValue.Multiple ->
      SelectionValue.Multiple(selectionValue.selection.addOrRemove(date))
    is SelectionValue.Period -> when {
      date.isBefore(selectionValue.start) -> selectionValue.copy(
        start = date,
        end = null,
      )
      date.isAfter(selectionValue.start) -> selectionValue.copy(end = date)
      date == selectionValue.start -> SelectionValue.None
      else -> selectionValue
    }
  }
}
