@file:Suppress("MatchingDeclarationName", "LongParameterList")

package io.github.boguszpawlowski.composecalendar

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
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.month.DaysOfWeek
import io.github.boguszpawlowski.composecalendar.month.MonthContent
import io.github.boguszpawlowski.composecalendar.month.MonthPager
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.DefaultWeekHeader
import io.github.boguszpawlowski.composecalendar.week.rotateRight
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * State of the calendar composable
 *
 * @property monthState currently showed month
 * @property selectionState handler for the calendar's selection
 */
@Stable
public class CalendarState<T : SelectionState>(
  public val monthState: MonthState,
  public val selectionState: T,
)

/**
 * [Calendar] implementation using a [DynamicSelectionState] as a selection handler.
 *
 *  * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    SelectableCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
public fun SelectableCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  horizontalSwipeEnabled: Boolean = true,
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
    today = today,
    showAdjacentMonths = showAdjacentMonths,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    calendarState = calendarState,
    dayContent = dayContent,
    monthHeader = monthHeader,
    weekHeader = weekHeader,
    monthContainer = monthContainer
  )
}

/**
 * [Calendar] implementation without any mechanism for the selection.
 *
 * Basic usage:
 * ```
 *  @Composable
 *  fun MainScreen() {
 *    StaticCalendar()
 *  }
 * ```
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
public fun StaticCalendar(
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  horizontalSwipeEnabled: Boolean = true,
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
    today = today,
    showAdjacentMonths = showAdjacentMonths,
    horizontalSwipeEnabled = horizontalSwipeEnabled,
    calendarState = calendarState,
    dayContent = dayContent,
    monthHeader = monthHeader,
    weekHeader = weekHeader,
    monthContainer = monthContainer
  )
}

/**
 * Composable for showing a calendar. The calendar state has to be provided by the call site. If you
 * want to use built-in implementation, check out:
 * [SelectableCalendar] - calendar composable handling selection that can be changed at runtime
 * [StaticCalendar] - calendar without any mechanism for selection
 *
 * @param modifier
 * @param firstDayOfWeek first day of a week, defaults to current locale's
 * @param today current day, defaults to [LocalDate.now]
 * @param showAdjacentMonths whenever to show or hide the days from adjacent months
 * @param horizontalSwipeEnabled whenever user is able to change the month by horizontal swipe
 * @param calendarState state of the composable
 * @param dayContent composable rendering the current day
 * @param monthHeader header for showing the current month and controls for changing it
 * @param weekHeader header for showing captions for each day of week
 * @param monthContainer container composable for all the days in current month
 */
@Composable
public fun <T : SelectionState> Calendar(
  calendarState: CalendarState<T>,
  modifier: Modifier = Modifier,
  firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
  today: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  horizontalSwipeEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit = { DefaultDay(it) },
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekHeader(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
) {

  val daysOfWeek = remember(firstDayOfWeek) {
    DayOfWeek.values().rotateRight(DaysOfWeek - firstDayOfWeek.ordinal)
  }

  Column(
    modifier = modifier,
  ) {
    monthHeader(calendarState.monthState)
    if (horizontalSwipeEnabled) {
      MonthPager(
        showAdjacentMonths = showAdjacentMonths,
        monthState = calendarState.monthState,
        selectionState = calendarState.selectionState,
        today = today,
        daysOfWeek = daysOfWeek,
        dayContent = dayContent,
        weekHeader = weekHeader,
        monthContainer = monthContainer,
      )
    } else {
      MonthContent(
        currentMonth = calendarState.monthState.currentMonth,
        showAdjacentMonths = showAdjacentMonths,
        selectionState = calendarState.selectionState,
        today = today,
        daysOfWeek = daysOfWeek,
        dayContent = dayContent,
        weekHeader = weekHeader,
        monthContainer = monthContainer,
      )
    }
  }
}

/**
 * Helper function for providing a [CalendarState] implementation with selection mechanism.
 *
 * @param initialMonth initially rendered month
 * @param initialSelection initial selection of the composable
 * @param initialSelectionMode initial mode of the selection
 * @param confirmSelectionChange callback for optional side-effects handling and vetoing the state change
 */
@Composable
public fun rememberSelectableCalendarState(
  initialMonth: YearMonth = YearMonth.now(),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialMonth)
  },
  selectionState: DynamicSelectionState = rememberSaveable(
    saver = DynamicSelectionState.Saver(confirmSelectionChange),
  ) {
    DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
  },
): CalendarState<DynamicSelectionState> = remember { CalendarState(monthState, selectionState) }

/**
 * Helper function for providing a [CalendarState] implementation without a selection mechanism.
 *
 * @param initialMonth initially rendered month
 */
@Composable
public fun rememberCalendarState(
  initialMonth: YearMonth = YearMonth.now(),
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialMonth)
  },
): CalendarState<EmptySelectionState> = remember { CalendarState(monthState, EmptySelectionState) }
