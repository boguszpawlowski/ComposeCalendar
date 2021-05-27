package com.bpawlowski.composecalendar.selection

import com.bpawlowski.composecalendar.util.addOrRemoveIfExists
import java.time.LocalDate

public object SelectionHandler {
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
      date.isBefore(selection.startOrMax()) -> listOf(date)
      date.isAfter(selection.startOrMax()) -> selection.fillUpTo(date)
      date == selection.startOrMax() -> emptyList()
      else -> selection
    }
  }
}

internal fun Collection<LocalDate>.startOrMax() = firstOrNull() ?: LocalDate.MAX
internal fun Collection<LocalDate>.endOrNull() = drop(1).lastOrNull()
internal fun Collection<LocalDate>.fillUpTo(date: LocalDate) =
  (0..date.toEpochDay() - first().toEpochDay()).map {
    first().plusDays(it)
  }
