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
import io.github.boguszpawlowski.composecalendar.week.DefaultDaysOfWeekHeader
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

/**
 * State of the week calendar composable
 *
 * @property weekState currently showed week
 * @property selectionState handler for the calendar's selection
 */
@Stable
public class WeekCalendarState<T : SelectionState>(
  public val weekState: WeekState,
  public val selectionState: T,
)

/**
 * [WeekCalendar] implementation using a [DynamicSelectionState] as a selection handler.
 *
 *  * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    SelectableWeekCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param horizontalSwipeEnabled whenever user is able to change the week by horizontal swipe
 * @param weekDaysScrollEnabled if the week days should be scrollable
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param weekHeader header for showing the current week and controls for changing it
 * @param daysOfWeekHeader header for showing captions for each day of week
 */
@Composable
public fun SelectableWeekCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  horizontalSwipeEnabled: Boolean = true,
  weekDaysScrollEnabled: Boolean = true,
  calendarState: WeekCalendarState<DynamicSelectionState> = rememberSelectableWeekCalendarState(),
  dayContent: @Composable BoxScope.(DayState<DynamicSelectionState>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultDaysOfWeekHeader(it) },
) {
  WeekCalendar(
    modifier = modifier,
    calendarState = calendarState,
    today = today,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    firstDayOfWeek = firstDayOfWeek,
    weekDaysScrollEnabled = weekDaysScrollEnabled,
    dayContent = dayContent,
    weekHeader = weekHeader,
    daysOfWeekHeader = daysOfWeekHeader,
  )
}

/**
 * [WeekCalendar] implementation without any mechanism for the selection.
 *
 *  * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    StaticWeekCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param horizontalSwipeEnabled whenever user is able to change the week by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param weekHeader header for showing the current week and controls for changing it
 * @param daysOfWeekHeader header for showing captions for each day of week
 */
@Composable
public fun StaticWeekCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  horizontalSwipeEnabled: Boolean = true,
  calendarState: WeekCalendarState<EmptySelectionState> = rememberWeekCalendarState(),
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<EmptySelectionState>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultDaysOfWeekHeader(it) },
) {
  WeekCalendar(
    modifier = modifier,
    calendarState = calendarState,
    today = today,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    firstDayOfWeek = firstDayOfWeek,
    weekDaysScrollEnabled = weekDaysScrollEnabled,
    dayContent = dayContent,
    weekHeader = weekHeader,
    daysOfWeekHeader = daysOfWeekHeader,
  )
}

/**
 * Composable for showing a week calendar. The calendar state has to be provided by the call site. If you
 * want to use built-in implementation, check out:
 * [SelectableWeekCalendar] - calendar composable handling selection that can be changed at runtime
 * [StaticWeekCalendar] - calendar without any mechanism for selection
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param horizontalSwipeEnabled whenever user is able to change the week by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param weekHeader header for showing the current week and controls for changing it
 * @param daysOfWeekHeader header for showing captions for each day of week
 */
@Composable
public fun <T : SelectionState> WeekCalendar(
  calendarState: WeekCalendarState<T>,
  modifier: Modifier = Modifier,
  today: LocalDate = LocalDate.now(),
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  horizontalSwipeEnabled: Boolean = true,
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DefaultDay(it) },
  weekHeader: @Composable ColumnScope.(WeekState) -> Unit = {
    DefaultProperWeekHeader(it)
  },
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultDaysOfWeekHeader(it) },
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
        weekDaysScrollEnabled = weekDaysScrollEnabled,
        dayContent = dayContent,
        daysOfWeekHeader = daysOfWeekHeader,
      )
    } else {
      WeekContent(
        daysOfWeek = daysOfWeek,
        weekDays = calendarState.weekState.currentWeek.getWeekDays(today),
        selectionState = calendarState.selectionState,
        weekDaysScrollEnabled = weekDaysScrollEnabled,
        dayContent = dayContent,
        daysOfWeekHeader = daysOfWeekHeader,
      )
    }
  }
}

/**
 * Helper function for providing a [WeekCalendarState] implementation with selection mechanism.
 *
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param initialWeek initially rendered month
 * @param initialSelection initial selection of the composable
 * @param initialSelectionMode initial mode of the selection
 * @param confirmSelectionChange callback for optional side-effects handling and vetoing the state change
 */
@Composable
public fun rememberSelectableWeekCalendarState(
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  initialWeek: Week = Week.now(firstDayOfWeek),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
  weekState: WeekState = rememberSaveable(saver = WeekState.Saver()) {
    WeekState(initialWeek = initialWeek)
  },
  selectionState: DynamicSelectionState = rememberSaveable(
    saver = DynamicSelectionState.Saver(confirmSelectionChange),
  ) {
    DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
  },
): WeekCalendarState<DynamicSelectionState> =
  remember { WeekCalendarState(weekState, selectionState) }

/**
 * Helper function for providing a [WeekCalendarState] implementation without a selection mechanism.
 *
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param initialWeek initially rendered week
 */
@Composable
public fun rememberWeekCalendarState(
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  initialWeek: Week = Week.now(firstDayOfWeek),
  weekState: WeekState = rememberSaveable(saver = WeekState.Saver()) {
    WeekState(initialWeek = initialWeek)
  },
): WeekCalendarState<EmptySelectionState> =
  remember { WeekCalendarState(weekState, EmptySelectionState) }
