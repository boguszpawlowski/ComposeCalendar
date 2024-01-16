package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.CalendarState
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun MinMaxCalendarSample() {
  val calendarState = rememberCalendarState(
    initialMonth = YearMonth.now(),
    minMonth = YearMonth.now(),
    maxMonth = YearMonth.now()
  )

  Column(
    Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    StaticCalendar(calendarState = calendarState)

    Spacer(modifier = Modifier.height(20.dp))

    MinMaxControls(calendarState = calendarState)
  }
}

@Composable
private fun MinMaxControls(
  calendarState: CalendarState<EmptySelectionState>,
) {
  val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM") }
  Text(
    text = "Calendar Min Month",
    style = MaterialTheme.typography.h5,
  )
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .clickable {
          calendarState.monthState.minMonth = calendarState.monthState.minMonth.minusMonths(1L)
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "-",
      textAlign = TextAlign.Center
    )
    val minYearMonthText = remember(calendarState.monthState.minMonth) { dateFormatter.format(calendarState.monthState.minMonth) }
    Text(text = minYearMonthText)
    Text(
      modifier = Modifier
        .clickable {
          if (calendarState.monthState.minMonth < calendarState.monthState.currentMonth) {
            calendarState.monthState.minMonth = calendarState.monthState.minMonth.plusMonths(1L)
          }
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "+",
      textAlign = TextAlign.Center
    )
  }
  
  Spacer(modifier = Modifier.height(20.dp))

  Text(
    text = "Calendar Max Month",
    style = MaterialTheme.typography.h5,
  )
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .clickable {
          if (calendarState.monthState.maxMonth > calendarState.monthState.currentMonth) {
            calendarState.monthState.maxMonth = calendarState.monthState.maxMonth.plusMonths(1L)
          }
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "-",
      textAlign = TextAlign.Center
    )
    val maxYearMonthText = remember(calendarState.monthState.maxMonth) { dateFormatter.format(calendarState.monthState.maxMonth) }
    Text(text = maxYearMonthText)
    Text(
      modifier = Modifier
        .clickable {
          calendarState.monthState.maxMonth = calendarState.monthState.maxMonth.plusMonths(1L)
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "+",
      textAlign = TextAlign.Center
    )
  }
}
