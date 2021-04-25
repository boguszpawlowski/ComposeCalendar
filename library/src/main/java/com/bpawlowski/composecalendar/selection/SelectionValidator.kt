package com.bpawlowski.composecalendar.selection

import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.None
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.util.addOrRemove
import java.time.LocalDate

internal class SelectionValidator(
  private val selectionMode: SelectionMode,
) {

  fun calculateNewSelection(
    date: LocalDate,
    value: SelectionValue
  ) = when (value) {
    SelectionValue.None -> when (selectionMode) {
      None -> SelectionValue.None
      Single -> SelectionValue.Single(date)
      Multiple -> SelectionValue.Multiple(listOf(date))
      Period -> SelectionValue.Period(start = date)
    }
    is SelectionValue.Single -> if (value.date == date) {
      SelectionValue.None
    } else {
      SelectionValue.Single(date)
    }
    is SelectionValue.Multiple ->
      SelectionValue.Multiple(value.selection.addOrRemove(date))
    is SelectionValue.Period -> when {
      date.isBefore(value.start) -> value.copy(
        start = date,
        end = null,
      )
      date.isAfter(value.start) -> value.copy(end = date)
      date == value.start -> SelectionValue.None
      else -> value
    }
  }

  fun validateSelection(newSelection: SelectionValue) {
    when (selectionMode) {
      None -> require(newSelection == SelectionValue.None)
      Single -> require(newSelection is SelectionValue.Single || newSelection == SelectionValue.None)
      Period -> require(newSelection is SelectionValue.Period || newSelection == SelectionValue.None)
      Multiple -> require(newSelection is SelectionValue.Multiple || newSelection == SelectionValue.None)
    }
  }
}
