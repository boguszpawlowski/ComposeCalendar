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
import io.github.boguszpawlowski.composecalendar.StaticWeekCalendar
import io.github.boguszpawlowski.composecalendar.WeekCalendarState
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.rememberWeekCalendarState
import io.github.boguszpawlowski.composecalendar.selection.EmptySelectionState
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun MinMaxCalendarSample() {
  val calendarState = rememberCalendarState(
    initialMonth = YearMonth.now(),
    minMonth = YearMonth.now(),
    maxMonth = YearMonth.now()
  )

  val weekCalendarState = rememberWeekCalendarState(
    initialWeek = Week.now(),
    minWeek = Week.now(),
    maxWeek = Week.now(),
  )

  Column(
    Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    StaticCalendar(calendarState = calendarState)

    Spacer(modifier = Modifier.height(20.dp))

    MinMaxControls(calendarState = calendarState)

    Spacer(modifier = Modifier.height(20.dp))

    StaticWeekCalendar(calendarState = weekCalendarState)

    Spacer(modifier = Modifier.height(20.dp))

    WeekMinMaxControls(calendarState = weekCalendarState)
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
  MinControls(
    calendarState = calendarState,
    dateFormatter = dateFormatter
  )
  Spacer(modifier = Modifier.height(20.dp))
  Text(
    text = "Calendar Max Month",
    style = MaterialTheme.typography.h5,
  )
  MaxControls(
    calendarState = calendarState,
    dateFormatter = dateFormatter
  )
}

@Composable
private fun MinControls(
  calendarState: CalendarState<EmptySelectionState>,
  dateFormatter: DateTimeFormatter,
) {
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
    val minYearMonthText = remember(calendarState.monthState.minMonth) {
      dateFormatter.format(calendarState.monthState.minMonth)
    }
    Text(text = minYearMonthText)
    Text(
      modifier = Modifier
        .clickable {
          calendarState.monthState.minMonth = calendarState.monthState.minMonth.plusMonths(1L)
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "+",
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun MaxControls(
  calendarState: CalendarState<EmptySelectionState>,
  dateFormatter: DateTimeFormatter,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .clickable {
          calendarState.monthState.maxMonth = calendarState.monthState.maxMonth.minusMonths(1L)
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "-",
      textAlign = TextAlign.Center
    )
    val maxYearMonthText = remember(calendarState.monthState.maxMonth) {
      dateFormatter.format(calendarState.monthState.maxMonth)
    }
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

@Composable
private fun WeekMinMaxControls(
  calendarState: WeekCalendarState<EmptySelectionState>,
) {
  val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM") }
  Text(
    text = "Calendar Min Week",
    style = MaterialTheme.typography.h5,
  )
  WeekMinControls(
    calendarState = calendarState,
    dateFormatter = dateFormatter
  )
  Spacer(modifier = Modifier.height(20.dp))
  Text(
    text = "Calendar Max Week",
    style = MaterialTheme.typography.h5,
  )
  WeekMaxControls(
    calendarState = calendarState,
    dateFormatter = dateFormatter
  )
}

@Composable
private fun WeekMinControls(
  calendarState: WeekCalendarState<EmptySelectionState>,
  dateFormatter: DateTimeFormatter,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .clickable {
          calendarState.weekState.minWeek = --calendarState.weekState.minWeek
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "-",
      textAlign = TextAlign.Center
    )
    val minYearMonthText = remember(calendarState.weekState.minWeek) {
      dateFormatter.format(calendarState.weekState.minWeek.start)
    }
    Text(text = minYearMonthText)
    Text(
      modifier = Modifier
        .clickable {
          calendarState.weekState.minWeek = ++calendarState.weekState.minWeek
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "+",
      textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun WeekMaxControls(
  calendarState: WeekCalendarState<EmptySelectionState>,
  dateFormatter: DateTimeFormatter,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .clickable {
          calendarState.weekState.maxWeek = --calendarState.weekState.maxWeek
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "-",
      textAlign = TextAlign.Center
    )
    val maxYearMonthText = remember(calendarState.weekState.maxWeek) {
      dateFormatter.format(calendarState.weekState.maxWeek.start)
    }
    Text(text = maxYearMonthText)
    Text(
      modifier = Modifier
        .clickable {
          calendarState.weekState.maxWeek = ++calendarState.weekState.maxWeek
        }
        .size(25.dp)
        .border(1.dp, Color.Black),
      text = "+",
      textAlign = TextAlign.Center
    )
  }
}
