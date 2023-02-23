@file:Suppress("MatchingDeclarationName", "LongParameterList")

package io.github.boguszpawlowski.composecalendar

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import io.github.boguszpawlowski.composecalendar.header.WeekState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DaysInAWeek
import io.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import io.github.boguszpawlowski.composecalendar.week.Week
import io.github.boguszpawlowski.composecalendar.week.WeekContent
import io.github.boguszpawlowski.composecalendar.week.WeekPager
import io.github.boguszpawlowski.composecalendar.week.getWeekDays
import io.github.boguszpawlowski.composecalendar.week.rotateRight
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import io.github.boguszpawlowski.composecalendar.header.DefaultWeekHeader as DefaultProperWeekHeader

@Stable
public class WeekCalendarState<T : SelectionState>(
  public val weekState: WeekState,
  public val selectionState: T,
)

@Composable
public fun SelectableWeekCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  horizontalSwipeEnabled: Boolean = true,
  calendarState: WeekCalendarState<DynamicSelectionState> = rememberSelectableWeekCalendarState(),
  dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
) {
  WeekCalendar(
    modifier = modifier,
    calendarState = calendarState,
    today = today,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    firstDayOfWeek = firstDayOfWeek,
    dayContent = dayContent,
    weekHeader = weekHeader,
    daysOfWeekHeader = daysOfWeekHeader,
  )
}

@Composable
public fun StaticWeekCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  horizontalSwipeEnabled: Boolean = true,
  weekCalendarState: WeekCalendarState<EmptySelectionState> = rememberWeekCalendarState(
    firstDayOfWeek = firstDayOfWeek
  ),
  dayContent: @Composable BoxScope.(DayState<EmptySelectionState>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
) {
  WeekCalendar(
    modifier = modifier,
    calendarState = weekCalendarState,
    today = today,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    firstDayOfWeek = firstDayOfWeek,
    dayContent = dayContent,
    weekHeader = weekHeader,
    daysOfWeekHeader = daysOfWeekHeader,
  )
}

@Composable
public fun <T : SelectionState> WeekCalendar(
  calendarState: WeekCalendarState<T>,
  modifier: Modifier = Modifier,
  today: LocalDate = LocalDate.now(),
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  horizontalSwipeEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
) {
  val initialWeek = remember { calendarState.weekState.currentWeek }
  val daysOfWeek = remember(firstDayOfWeek) {
    DayOfWeek.values().rotateRight(DaysInAWeek - firstDayOfWeek.ordinal)
  }

  Column(
    modifier = modifier,
  ) {
    weekHeader(calendarState.weekState)
    if (horizontalSwipeEnabled) {
      WeekPager(
        initialWeek = initialWeek,
        daysOfWeek = daysOfWeek,
        weekState = calendarState.weekState,
        selectionState = calendarState.selectionState,
        today = today,
        dayContent = dayContent,
        daysOfWeekHeader = daysOfWeekHeader,
      )
    } else {
      WeekContent(
        daysOfWeek = daysOfWeek,
        weekDays = calendarState.weekState.currentWeek.getWeekDays(today),
        selectionState = calendarState.selectionState,
        dayContent = dayContent,
        daysOfWeekHeader = daysOfWeekHeader,
      )
    }
  }
}

@Composable
public fun rememberSelectableWeekCalendarState(
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  initialWeek: Week = Week.now(firstDayOfWeek),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
  monthState: WeekState = rememberSaveable(saver = WeekState.Saver()) {
    WeekState(initialWeek = initialWeek)
  },
  selectionState: DynamicSelectionState = rememberSaveable(
    saver = DynamicSelectionState.Saver(confirmSelectionChange),
  ) {
    DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
  },
): WeekCalendarState<DynamicSelectionState> =
  remember { WeekCalendarState(monthState, selectionState) }

/**
 * Helper function for providing a [WeekCalendarState] implementation without a selection mechanism.
 *
 * @param initialWeek initially rendered week
 */
@Composable
public fun rememberWeekCalendarState(
  firstDayOfWeek: DayOfWeek,
  initialWeek: Week = Week.now(firstDayOfWeek),
  monthState: WeekState = rememberSaveable(saver = WeekState.Saver()) {
    WeekState(initialWeek = initialWeek)
  },
): WeekCalendarState<EmptySelectionState> =
  remember { WeekCalendarState(monthState, EmptySelectionState) }
