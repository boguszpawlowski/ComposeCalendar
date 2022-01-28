package io.github.boguszpawlowski.composecalendar.selection

import io.github.boguszpawlowski.composecalendar.util.addOrRemoveIfExists
import kotlinx.datetime.LocalDate

/**
 * Helper class for calculating new selection, when using a [DynamicSelectionState] approach.
 * @param date clicked on date
 * @param selection current selection
 * @param selectionMode current selection mode
 * @returns new selection in a form of a list of local dates.
 */
public object DynamicSelectionHandler {
  public fun calculateNewSelection(
    date: LocalDate,
    selection: List<LocalDate>,
    selectionMode: SelectionMode,
  ): List<LocalDate> = when (selectionMode) {
    SelectionMode.None -> emptyList()
    SelectionMode.Single -> if (date == selection.firstOrNull()) {
      emptyList()
    } else {
      listOf(date)
    }
    SelectionMode.Multiple -> selection.addOrRemoveIfExists(date)
    SelectionMode.Period -> when {
      date.compareTo(selection.startOrMax()) < 0 -> listOf(date)
      //isBefore(selection.startOrMax()) -> listOf(date)
      date.compareTo(selection.startOrMax()) > 0 -> selection.fillUpTo(date)
      //date.isAfter(selection.startOrMax()) -> selection.fillUpTo(date)
      date == selection.startOrMax() -> emptyList()
      else -> selection
    }

  }
}
