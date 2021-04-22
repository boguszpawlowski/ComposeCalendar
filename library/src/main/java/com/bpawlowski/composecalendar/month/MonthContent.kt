package com.bpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.week.WeekContent
import com.bpawlowski.composecalendar.week.getWeeks
import java.time.LocalDate

@Composable
fun MonthContent(
  month: Month,
  modifier: Modifier = Modifier,
) {

  Column(modifier = modifier) {
    month.yearMonth.getWeeks().forEach { week ->
      WeekContent(week = week)
    }
  }
}
