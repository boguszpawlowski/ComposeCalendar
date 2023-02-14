package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.boguszpawlowski.composecalendar.Calendar
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.states.ModeState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import io.github.boguszpawlowski.composecalendar.states.DayEvent
import io.github.boguszpawlowski.composecalendar.states.EventState
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomSelectionSample() {
  val scrollState = rememberScrollState()
  Column(modifier = Modifier
    .fillMaxWidth()
    .verticalScroll(scrollState)
  ) {
    val calendarState = rememberMonthSelectionState(
      eventState = EventState( dayEventList )
    )
    ModeControls(modeState = calendarState.modeState)
    Calendar(calendarState = calendarState)
  }
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
  initialDay: LocalDate = LocalDate.now(),
  initialSelection: YearMonth? = null,
  initialMonthMode: Boolean = true,
  initialEventList: List<DayEvent> = emptyList(),
  currentState: CurrentState = rememberSaveable(saver = CurrentState.Saver()) {
    CurrentState(initialDay = initialDay)
  },
  selectionState: MonthSelectionState = rememberSaveable(saver = MonthSelectionState.Saver()) {
    MonthSelectionState(initialSelection = initialSelection)
  },
  modeState: ModeState = rememberSaveable(saver = ModeState.Saver()) {
    ModeState(initialMonthMode = initialMonthMode)
  },
  eventState: EventState = rememberSaveable(saver = EventState.Saver()) {
    EventState(initialEventList)
  },
): CalendarState<MonthSelectionState> = remember { CalendarState(
  currentState = currentState,
  modeState = modeState,
  selectionState = selectionState,
  eventState = eventState,
) }

private val LocalDate.yearMonth: YearMonth
  get() = YearMonth.of(year, month)
