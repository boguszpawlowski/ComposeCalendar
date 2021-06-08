package com.bpawlowski.composecalendar.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.bpawlowski.composecalendar.Calendar
import com.bpawlowski.composecalendar.CalendarState
import com.bpawlowski.composecalendar.header.MonthState
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.util.yearMonth
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomSelectionSample() {
  Calendar(calendarState = rememberMonthSelectionState())
}

private class MonthSelectionState(
  initialSelection: YearMonth? = null,
) : SelectionState {

  private var selection by mutableStateOf(initialSelection)

  override fun isDateSelected(date: LocalDate): Boolean =
    date.yearMonth == selection

  override fun onDateSelected(date: LocalDate) {
    selection = if (date.yearMonth == selection) null else date.yearMonth
  }

  companion object {
    @Suppress("FunctionName") // Factory function
    fun Saver(): Saver<MonthSelectionState, Any> = Saver(
      save = { it.selection?.toString() },
      restore = { restored ->
        val selection = (restored as? String)?.let { YearMonth.parse(it) }
        MonthSelectionState(selection)
      }
    )
  }
}

@Composable
private fun rememberMonthSelectionState(
  initialDate: LocalDate = LocalDate.now(),
  initialSelection: YearMonth? = null,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
  selectionState: MonthSelectionState = rememberSaveable(saver = MonthSelectionState.Saver()) {
    MonthSelectionState(initialSelection = initialSelection)
  }
): CalendarState<MonthSelectionState> = remember { CalendarState(monthState, selectionState) }
