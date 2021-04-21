package com.bpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.bpawlowski.composecalendar.day.Day
import com.bpawlowski.composecalendar.day.DayState
import com.bpawlowski.composecalendar.day.EmptyDay

@Composable
fun Month(
  monthState: MonthState,
) {
  val month = monthState.month
  val daysLength = month.lengthOfMonth()

  val dayOffset = month.atDay(1).dayOfWeek.ordinal

  Column {
    (1..daysLength + dayOffset).chunked(7).forEachIndexed { index, daysInWeek ->
      Row {
        daysInWeek.forEach {
          if (index == 0 && it <= dayOffset) {
            EmptyDay()
          } else {
            val date = month.atDay(it - dayOffset)
            Day(DayState(date = date))
          }
        }
      }
    }
  }
}
