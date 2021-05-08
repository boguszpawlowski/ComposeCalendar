package com.bpawlowski.composecalendar.selection

import com.bpawlowski.composecalendar.selection.SelectionValue.Multiple
import com.bpawlowski.composecalendar.selection.SelectionValue.None
import com.bpawlowski.composecalendar.selection.SelectionValue.Period
import com.bpawlowski.composecalendar.selection.SelectionValue.Single
import com.bpawlowski.composecalendar.util.addOrRemoveIfExists
import java.time.LocalDate

public object SelectionHandler {
  public fun calculateNewSelection(
    date: LocalDate,
    selectionValue: SelectionValue,
    selectionMode: SelectionMode,
  ): SelectionValue = when (selectionValue) {
    None -> when (selectionMode) {
      SelectionMode.None -> None
      SelectionMode.Single -> Single(date)
      SelectionMode.Multiple -> Multiple(listOf(date))
      SelectionMode.Period -> Period(start = date)
    }
    is Single -> if (selectionValue.selection == date) {
      None
    } else {
      Single(date)
    }
    is Multiple ->
      Multiple(selectionValue.selection.addOrRemoveIfExists(date))
    is Period -> when {
      date.isBefore(selectionValue.start) -> selectionValue.copy(
        start = date,
        end = null,
      )
      date.isAfter(selectionValue.start) -> selectionValue.copy(end = date)
      date == selectionValue.start -> None
      else -> selectionValue
    }
  }
}
