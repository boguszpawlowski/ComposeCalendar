package com.bpawlowski.composecalendar.selection

import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.None
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.util.addOrRemove
import java.time.LocalDate

internal object SelectionValidator {

  fun calculateNewSelection(
    date: LocalDate,
    selectionValue: SelectionValue,
    selectionMode: SelectionMode,
  ) = when (selectionValue) {
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

  fun validateSelection(newSelection: SelectionValue, selectionMode: SelectionMode) {
    when (selectionMode) {
      None -> require(newSelection == SelectionValue.None)
      Single -> require(newSelection is SelectionValue.Single || newSelection == SelectionValue.None)
      Period -> require(newSelection is SelectionValue.Period || newSelection == SelectionValue.None)
      Multiple -> require(newSelection is SelectionValue.Multiple || newSelection == SelectionValue.None)
    }
  }
}
