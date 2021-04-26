package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.config.CalendarConfig
import com.bpawlowski.composecalendar.day.Day
import com.bpawlowski.composecalendar.header.MonthHeader
import com.bpawlowski.composecalendar.header.MonthState
import com.bpawlowski.composecalendar.month.Month
import com.bpawlowski.composecalendar.month.MonthContent
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionMode.Single
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.selection.SelectionValue
import com.bpawlowski.composecalendar.util.yearMonth
import java.time.LocalDate

@Suppress("UnusedPrivateMember") // STOPSHIP: 22/04/2021 Remove once not true
@Composable
public fun Calendar(
  modifier: Modifier = Modifier,
  currentDate: LocalDate = LocalDate.now(),
  config: CalendarConfig = CalendarConfig(),
  calendarState: CalendarState = rememberCalendarState(),
  dayContent: @Composable RowScope.(Day) -> Unit = {},
  monthHeader: @Composable ColumnScope.(MonthState) -> Unit = { MonthHeader(it) },
) {

  Column(
    modifier = modifier,
  ) {
    monthHeader(calendarState.monthState)
    MonthContent(
      showAdjacentMonths = config.showAdjacentMonths,
      month = Month(calendarState.monthState.currentMonth, currentDate = currentDate),
      selectionState = calendarState.selectionState,
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
  selectionMode: SelectionMode = Single,
  monthState: MonthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = initialDate.yearMonth)
  },
  selectionState: SelectionState = rememberSaveable(saver = SelectionState.Saver()) {
    SelectionState(
      initialSelection = initialSelection,
      selectionMode = selectionMode
    )
  },
): CalendarState = remember { CalendarState(monthState, selectionState) }
