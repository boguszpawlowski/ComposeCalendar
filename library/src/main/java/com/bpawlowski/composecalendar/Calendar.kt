package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.Day
import com.bpawlowski.composecalendar.month.Month
import com.bpawlowski.composecalendar.month.MonthContent
import java.time.LocalDate
import java.time.YearMonth

@Suppress("UnusedPrivateMember")
@Composable
fun Calendar(
  modifier: Modifier = Modifier,
  initialDate: LocalDate = LocalDate.now(),
  currentDate: LocalDate = LocalDate.now(),
  dayContent: @Composable (Day) -> Unit = {},
) {
  val (displayedMonth, setDisplayedMonth) = remember {
    mutableStateOf(
      YearMonth.of(
        initialDate.year,
        initialDate.month
      )
    )
  }

  Column {
    HeaderContent(
      header = Header(displayedMonth),
      modifier = Modifier.fillMaxWidth(),
      onCurrentMonthChanged = { setDisplayedMonth(it) }
    )
    MonthContent(
      month = Month(displayedMonth, currentDate = currentDate),
    )
  }
}
