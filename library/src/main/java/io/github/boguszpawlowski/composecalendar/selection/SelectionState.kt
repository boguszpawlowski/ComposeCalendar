package io.github.boguszpawlowski.composecalendar.selection

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

/**
 * Class that enables for dynamically changing selection modes in the runtime. Depending on the mode, selection changes differently.
 * Mode can be varied by setting desired [SelectionMode] in the [selectionMode] mutable property.
 */
@Stable
public class DynamicSelectionState(
  private val onSelectionChanged: (List<LocalDate>) -> Unit,
  selection: List<LocalDate>,
  selectionMode: SelectionMode,
) : SelectionState {

  private var _selection by mutableStateOf(selection)
  private var _selectionMode by mutableStateOf(selectionMode)

  public var selection: List<LocalDate>
    get() = _selection
    set(value) {
      if (value != selection) {
        _selection = value
        onSelectionChanged(value)
      }
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
    selection = DynamicSelectionHandler.calculateNewSelection(date, selection, selectionMode)
  }

  internal companion object {
    @Suppress("FunctionName") // Factory function
    fun Saver(onSelectionChanged: (List<LocalDate>) -> Unit): Saver<DynamicSelectionState, Any> =
      listSaver(
        save = {
          listOf(it.selectionMode, it.selection.map { it.toString() })
        },
        restore = { restored ->
          DynamicSelectionState(
            onSelectionChanged = onSelectionChanged,
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
