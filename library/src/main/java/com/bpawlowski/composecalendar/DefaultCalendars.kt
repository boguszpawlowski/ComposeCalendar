package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayState
import com.bpawlowski.composecalendar.day.DefaultDay
import com.bpawlowski.composecalendar.header.DefaultMonthHeader
import com.bpawlowski.composecalendar.header.MonthState
import com.bpawlowski.composecalendar.selection.DynamicSelectionState
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SingleSelectionState
import com.bpawlowski.composecalendar.util.yearMonth
import com.bpawlowski.composecalendar.week.DefaultWeekHeader
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
public fun SingleSelectionCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  calendarState: CalendarState<SingleSelectionState> = rememberSingleCalendarState(),
  dayContent: @Composable BoxScope.(DayState<SingleSelectionState>) -> Unit = { DefaultDay(it) },
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
) {
  Calendar(
    modifier = modifier,
    firstDayOfWeek = firstDayOfWeek,
    currentDate = currentDate,
    showAdjacentMonths = showAdjacentMonths,
    calendarState = calendarState,
    dayContent = dayContent,
    monthHeader = monthHeader,
    weekHeader = weekHeader,
    monthContainer = monthContainer
  )
}

@Composable
public fun DynamicSelectionCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  calendarState: CalendarState<DynamicSelectionState> = rememberDynamicCalendarState(),
  dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DefaultDay(it) },
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
) {
  Calendar(
    modifier = modifier,
    firstDayOfWeek = firstDayOfWeek,
    currentDate = currentDate,
    showAdjacentMonths = showAdjacentMonths,
    calendarState = calendarState,
    dayContent = dayContent,
    monthHeader = monthHeader,
    weekHeader = weekHeader,
    monthContainer = monthContainer
  )
}

@Composable
public fun rememberSingleCalendarState(
  initialSelection: LocalDate? = null
): CalendarState<SingleSelectionState> = rememberCalendarState(
  selectionState = rememberSaveable(saver = SingleSelectionState.Saver()) {
    SingleSelectionState(initialSelection)
  }
)

@Composable
public fun rememberDynamicCalendarState(
  initialDate: LocalDate = LocalDate.now(),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
  selectionState: DynamicSelectionState = rememberSaveable(saver = DynamicSelectionState.Saver()) {
    DynamicSelectionState(initialSelection, initialSelectionMode)
  },
): CalendarState<DynamicSelectionState> =
  rememberCalendarState(selectionState = selectionState, monthState = monthState)
