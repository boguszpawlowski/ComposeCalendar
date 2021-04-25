package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bpawlowski.composecalendar.config.CalendarConfig
import com.bpawlowski.composecalendar.day.Day
import com.bpawlowski.composecalendar.header.Header
import com.bpawlowski.composecalendar.header.HeaderContent
import com.bpawlowski.composecalendar.month.Month
import com.bpawlowski.composecalendar.month.MonthContent
import java.time.LocalDate
import java.time.YearMonth

@Suppress("UnusedPrivateMember") // STOPSHIP: 22/04/2021 Remove once not true
@Composable
public fun Calendar(
  modifier: Modifier = Modifier,
  initialDate: LocalDate = LocalDate.now(),
  currentDate: LocalDate = LocalDate.now(),
  config: CalendarConfig = CalendarConfig(),
  dayContent: @Composable RowScope.(Day) -> Unit = {},
  headerContent: @Composable ColumnScope.(Header) -> Unit = {},
) {
  val (displayedMonth, setDisplayedMonth) = remember {
    mutableStateOf(
      YearMonth.of(
        initialDate.year,
        initialDate.month,
      )
    )
  }

  Column(
    modifier = modifier,
  ) {
    HeaderContent(
      header = Header(displayedMonth),
      modifier = Modifier.fillMaxWidth(),
      onCurrentMonthChanged = { setDisplayedMonth(it) }
    )
    MonthContent(
      showAdjacentMonths = config.showAdjacentMonths,
      month = Month(displayedMonth, currentDate = currentDate),
      modifier = Modifier.padding(8.dp),
    )
  }
}
