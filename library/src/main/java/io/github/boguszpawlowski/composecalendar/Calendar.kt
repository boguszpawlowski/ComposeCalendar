@file:Suppress("MatchingDeclarationName", "LongParameterList")

package io.github.boguszpawlowski.composecalendar

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.DefaultDay
import io.github.boguszpawlowski.composecalendar.header.DefaultMonthHeader
import io.github.boguszpawlowski.composecalendar.header.DefaultWeekHeader
import io.github.boguszpawlowski.composecalendar.month.DaysOfWeek
import io.github.boguszpawlowski.composecalendar.month.MonthContent
import io.github.boguszpawlowski.composecalendar.month.MonthPager
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.header.DefaultWeekDaysNames
import io.github.boguszpawlowski.composecalendar.week.WeekContent
import io.github.boguszpawlowski.composecalendar.header.rotateRight
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import io.github.boguszpawlowski.composecalendar.states.DayEvent
import io.github.boguszpawlowski.composecalendar.states.EventState
import io.github.boguszpawlowski.composecalendar.states.ModeState
import io.github.boguszpawlowski.composecalendar.week.WeekPager
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * State of the calendar composable
 *
 * @property monthState currently showed month
 * @property weekState showed first day of currently week
 * @property selectionState handler for the calendar's selection
 */
@Stable
public class CalendarState<T : SelectionState>(
  public val currentState: CurrentState,
  public val modeState: ModeState,
  public val selectionState: T,
  public val eventState: EventState,
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
  monthHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultWeekHeader(it) },
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekDaysNames(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  weekContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  onSwipe: (LocalDate) -> Unit = { },
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
    weekDaysNames = weekDaysNames,
    monthContainer = monthContainer,
    weekContainer = weekContainer,
    onSwipe = onSwipe,
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
  monthHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultWeekHeader(it) },
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekDaysNames(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  weekContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  onSwipe: (LocalDate) -> Unit = { },
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
    weekDaysNames = weekDaysNames,
    monthContainer = monthContainer,
    weekContainer = weekContainer,
    onSwipe = onSwipe,
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
  monthHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable ColumnScope.(CurrentState) -> Unit = { DefaultWeekHeader(it) },
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit = { DefaultWeekDaysNames(it) },
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  weekContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { content ->
    Box { content(PaddingValues()) }
  },
  onSwipe: (LocalDate) -> Unit = { },
) {

  val daysOfWeek = remember(firstDayOfWeek) {
    DayOfWeek.values().rotateRight(DaysOfWeek - firstDayOfWeek.ordinal)
  }

  Column(
    modifier = modifier
      .animateContentSize(),
  ) {
    if (!calendarState.modeState.isMonthMode) {
      weekHeader(calendarState.currentState)
      if (horizontalSwipeEnabled) {
        val initialDay = remember { calendarState.currentState.day }
        WeekPager(
          initialDay = initialDay,
          selectionState = calendarState.selectionState,
          currentState = calendarState.currentState,
          eventState = calendarState.eventState,
          daysOfWeek = daysOfWeek,
          today = today,
          dayContent = dayContent,
          weekDaysNames = weekDaysNames,
          weekContainer = weekContainer,
          onSwipe = onSwipe
        )
      } else {
        WeekContent(
          selectionState = calendarState.selectionState,
          currentDay = calendarState.currentState.day,
          eventState = calendarState.eventState,
          daysOfWeek = daysOfWeek,
          today = today,
          dayContent = dayContent,
          weekDaysNames = weekDaysNames,
          weekContainer = weekContainer,
        )
      }
    } else {
      monthHeader(calendarState.currentState)
      if (horizontalSwipeEnabled) {
        MonthPager(
          initialMonth = YearMonth.of(
            calendarState.currentState.day.year,
            calendarState.currentState.day.month
          ),
          showAdjacentMonths = showAdjacentMonths,
          currentState = calendarState.currentState,
          selectionState = calendarState.selectionState,
          eventState = calendarState.eventState,
          today = today,
          daysOfWeek = daysOfWeek,
          dayContent = dayContent,
          weekDaysNames = weekDaysNames,
          monthContainer = monthContainer,
          onSwipe = onSwipe,
        )
      } else {
        MonthContent(
          modifier = Modifier.fillMaxWidth(),
          currentMonth = YearMonth.of(
            calendarState.currentState.day.year,
            calendarState.currentState.day.month
          ),
          showAdjacentMonths = showAdjacentMonths,
          selectionState = calendarState.selectionState,
          eventState = calendarState.eventState,
          today = today,
          daysOfWeek = daysOfWeek,
          dayContent = dayContent,
          weekDaysNames = weekDaysNames,
          monthContainer = monthContainer,
        )
      }
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
  initialDay: LocalDate = LocalDate.now(),
  initialMonthMode: Boolean = true,
  initialEventList: List<DayEvent> = emptyList(),
  initialSelection: List<LocalDate> = emptyList(),
  initialSelectionMode: SelectionMode = SelectionMode.Single,
  confirmSelectionChange: (newValue: List<LocalDate>) -> Boolean = { true },
  modeState: ModeState = rememberSaveable(saver = ModeState.Saver()) {
    ModeState(initialMonthMode = initialMonthMode)
  },
  currentState: CurrentState = rememberSaveable(saver = CurrentState.Saver()) {
    CurrentState(initialDay)
  },
  selectionState: DynamicSelectionState = rememberSaveable(
    saver = DynamicSelectionState.Saver(confirmSelectionChange),
  ) {
    DynamicSelectionState(confirmSelectionChange, initialSelection, initialSelectionMode)
  },
  eventState: EventState = rememberSaveable(saver = EventState.Saver()) {
    EventState(initialEventList)
  },
): CalendarState<DynamicSelectionState> = remember { CalendarState(
  currentState = currentState,
  selectionState = selectionState,
  modeState = modeState,
  eventState = eventState,
) }

/**
 * Helper function for providing a [CalendarState] implementation without a selection mechanism.
 *
 * @param initialMonth initially rendered month
 */
@Composable
public fun rememberCalendarState(
  initialDay: LocalDate = LocalDate.now(),
  initialMonthMode: Boolean = true,
  initialEventList: List<DayEvent> = emptyList(),
  modeState: ModeState = rememberSaveable(saver = ModeState.Saver()) {
    ModeState(initialMonthMode = initialMonthMode)
  },
  currentState: CurrentState = rememberSaveable(saver = CurrentState.Saver()) {
    CurrentState(initialDay)
  },
  eventState: EventState = rememberSaveable(saver = EventState.Saver()) {
    EventState(initialEventList)
  },
): CalendarState<EmptySelectionState> = remember { CalendarState(
  currentState = currentState,
  modeState = modeState,
  selectionState = EmptySelectionState,
  eventState = eventState,
) }
