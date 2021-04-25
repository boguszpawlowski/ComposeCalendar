package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bpawlowski.composecalendar.config.CalendarConfig
import com.bpawlowski.composecalendar.day.Day
import com.bpawlowski.composecalendar.header.MonthHeader
import com.bpawlowski.composecalendar.header.MonthState
import com.bpawlowski.composecalendar.header.rememberMonthState
import com.bpawlowski.composecalendar.month.Month
import com.bpawlowski.composecalendar.month.MonthContent
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.selection.SelectionValue
import com.bpawlowski.composecalendar.selection.rememberSelectionState
import com.bpawlowski.composecalendar.util.yearMonth
import java.time.LocalDate

@Suppress("UnusedPrivateMember") // STOPSHIP: 22/04/2021 Remove once not true
@Composable
public fun Calendar(
  modifier: Modifier = Modifier,
  initialDate: LocalDate = LocalDate.now(),
  selectionMode: SelectionMode = SelectionMode.Period,
  initialSelection: SelectionValue = SelectionValue.None,
  currentDate: LocalDate = LocalDate.now(),
  config: CalendarConfig = CalendarConfig(),
  calendarState: CalendarState = rememberCalendarState(
    initialDate,
    initialSelection,
    selectionMode,
  ),
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
      modifier = Modifier.padding(8.dp),
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
  initialDate: LocalDate,
  initialSelection: SelectionValue,
  selectionMode: SelectionMode,
  monthState: MonthState = rememberMonthState(initialMonth = initialDate.yearMonth()),
  selectionState: SelectionState = rememberSelectionState(initialSelection, selectionMode),
): CalendarState = remember { CalendarState(monthState, selectionState) }
