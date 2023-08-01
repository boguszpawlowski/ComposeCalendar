package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomSelectionSample() {
  Calendar(
    calendarState = rememberMonthSelectionState(),
    onMonthSwipe = { Timber.tag("new month").d(it.toString()) },
  )
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
  initialMonth: YearMonth = YearMonth.now(),
  initialSelection: YearMonth? = null,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialMonth)
  },
  selectionState: MonthSelectionState = rememberSaveable(saver = MonthSelectionState.Saver()) {
    MonthSelectionState(initialSelection = initialSelection)
  },
): CalendarState<MonthSelectionState> = remember { CalendarState(monthState, selectionState) }

private val LocalDate.yearMonth: YearMonth
  get() = YearMonth.of(year, month)
