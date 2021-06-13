package com.github.boguszpawlowski.composecalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.github.boguszpawlowski.composecalendar.day.DayState
import com.github.boguszpawlowski.composecalendar.day.DefaultDay
import com.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import com.github.boguszpawlowski.composecalendar.header.MonthState
import com.github.boguszpawlowski.composecalendar.month.Month
import com.github.boguszpawlowski.composecalendar.month.MonthContent
import com.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import com.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import com.github.boguszpawlowski.composecalendar.selection.SelectionMode
import com.github.boguszpawlowski.composecalendar.selection.SelectionState
import com.github.boguszpawlowski.composecalendar.util.yearMonth
import com.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

@Composable
public fun SelectableCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  calendarState: CalendarState<DynamicSelectionState> = rememberSelectableCalendarState(),
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
public fun StaticCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  calendarState: CalendarState<EmptySelectionState> = rememberCalendarState(),
  dayContent: @Composable BoxScope.(DayState<EmptySelectionState>) -> Unit = { DefaultDay(it) },
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
public fun <T : SelectionState> Calendar(
  calendarState: CalendarState<T>,
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DefaultDay(it) },
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
) {
  Column(
    modifier = modifier,
  ) {
    monthHeader(calendarState.monthState)
    MonthContent(
      showAdjacentMonths = showAdjacentMonths,
      month = Month(calendarState.monthState.currentMonth, currentDate = currentDate),
      selectionState = calendarState.selectionState,
      firstDayOfWeek = firstDayOfWeek,
      dayContent = dayContent,
      weekHeader = weekHeader,
      monthContainer = monthContainer,
    )
  }
}

@Stable
public class CalendarState<T : SelectionState>(
  public val monthState: MonthState,
  public val selectionState: T,
)

@Composable
public fun rememberSelectableCalendarState(
  initialDate: LocalDate = LocalDate.now(),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
  selectionState: DynamicSelectionState = rememberSaveable(saver = DynamicSelectionState.Saver()) {
    DynamicSelectionState(initialSelection, initialSelectionMode)
  },
): CalendarState<DynamicSelectionState> = remember { CalendarState(monthState, selectionState) }

@Composable
public fun rememberCalendarState(
  initialDate: LocalDate = LocalDate.now(),
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
): CalendarState<EmptySelectionState> = remember { CalendarState(monthState, EmptySelectionState) }
