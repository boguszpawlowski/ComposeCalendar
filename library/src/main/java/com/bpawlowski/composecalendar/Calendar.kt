package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayState
import com.bpawlowski.composecalendar.day.DefaultDay
import com.bpawlowski.composecalendar.header.DefaultMonthHeader
import com.bpawlowski.composecalendar.header.MonthState
import com.bpawlowski.composecalendar.month.Month
import com.bpawlowski.composecalendar.month.MonthContent
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.selection.SelectionValue
import com.bpawlowski.composecalendar.util.yearMonth
import com.bpawlowski.composecalendar.week.DefaultWeekHeader
import java.time.LocalDate

@Composable
public fun Calendar(
  modifier: Modifier = Modifier,
  currentDate: LocalDate = LocalDate.now(),
  showAdjacentMonths: Boolean = true,
  calendarState: CalendarState = rememberCalendarState(),
  dayContent: @Composable BoxScope.(DayState) -> Unit = { DefaultDay(it) },
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { DefaultMonthHeader(it) },
  weekHeader: @Composable RowScope.() -> Unit = { DefaultWeekHeader() },
  monthContainer: @Composable (content: @Composable () -> Unit) -> Unit = { Box { it() } }
) {
  Column(
    modifier = modifier,
  ) {
    monthHeader(calendarState.monthState)
    MonthContent(
      showAdjacentMonths = showAdjacentMonths,
      month = Month(calendarState.monthState.currentMonth, currentDate = currentDate),
      selectionState = calendarState.selectionState,
      dayContent = dayContent,
      weekHeader = weekHeader,
      monthContainer = monthContainer,
    )
  }
}

@Stable
public class CalendarState(
  public val monthState: MonthState,
  public val selectionState: SelectionState,
)

@Composable
public fun rememberCalendarState(
  initialDate: LocalDate = LocalDate.now(),
  initialSelection: SelectionValue = SelectionValue.None,
  initialSelectionMode: SelectionMode = Single,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
  selectionState: SelectionState = rememberSaveable(saver = SelectionState.Saver()) {
    SelectionState(
      initialSelection = initialSelection,
      selectionMode = initialSelectionMode
    )
  },
): CalendarState = remember { CalendarState(monthState, selectionState) }
