package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.bpawlowski.composecalendar.selection.SelectionMode.Multiple
import com.bpawlowski.composecalendar.selection.SelectionMode.None
import com.bpawlowski.composecalendar.selection.SelectionMode.Period
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import java.time.LocalDate

@Composable
public fun rememberSelectionState(
  initialSelection: SelectionValue,
  selectionMode: SelectionMode
): SelectionState =
  remember { SelectionStateImpl(initialSelection, selectionMode) }

@Stable
public interface SelectionState {
  public val selectionValue: SelectionValue
  public fun onSelectionChanged(newSelection: SelectionValue)
  public fun onDateSelected(date: LocalDate)
}

@Stable
public class SelectionStateImpl(
  initialSelectionValue: SelectionValue,
  private val selectionMode: SelectionMode,
) : SelectionState {

  init {
    validateSelection(initialSelectionValue)
  }

  private var _selectionValue by mutableStateOf<SelectionValue>(initialSelectionValue)

  override val selectionValue: SelectionValue
    get() = _selectionValue

  override fun onSelectionChanged(newSelection: SelectionValue) {
    validateSelection(newSelection)
    _selectionValue = newSelection
  }

  override fun onDateSelected(date: LocalDate) {
    onSelectionChanged(calculateNewSelection(date, selectionMode, selectionValue))
  }

  private fun calculateNewSelection(
    date: LocalDate,
    mode: SelectionMode,
    value: SelectionValue
  ) = when (value) {
    SelectionValue.None -> when (mode) {
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

  private fun validateSelection(newSelection: SelectionValue) {
    when (selectionMode) {
      None -> require(newSelection == SelectionValue.None)
      Single -> require(newSelection is SelectionValue.Single || newSelection == SelectionValue.None)
      Period -> require(newSelection is SelectionValue.Period || newSelection == SelectionValue.None)
      Multiple -> require(newSelection is SelectionValue.Multiple || newSelection == SelectionValue.None)
    }
  }
}

private fun Collection<LocalDate>.addOrRemove(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }
