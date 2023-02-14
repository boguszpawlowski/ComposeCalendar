package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import io.github.boguszpawlowski.composecalendar.header.DefaultWeekDaysNames
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import java.time.DayOfWeek
import java.time.DayOfWeek.SUNDAY
import java.time.format.TextStyle.NARROW
import java.util.Locale

@Composable
fun CustomComponentsSample() {
  val scrollState = rememberScrollState()
  Column(modifier = Modifier
    .fillMaxWidth()
    .verticalScroll(scrollState)
  ) {
    val calendarState = rememberCalendarState()
    ModeControls(modeState = calendarState.modeState)
    StaticCalendar(
      modifier = Modifier.animateContentSize(),
      showAdjacentMonths = false,
      firstDayOfWeek = SUNDAY,
      monthContainer = { MonthContainer(it) },
      dayContent = { DayContent(dayState = it) },
      weekDaysNames = { DefaultWeekDaysNames(listDays = it) },
      monthHeader = { MonthHeader(currentState = it) },
      calendarState = calendarState
    )
  }
}

@Composable
private fun DayContent(dayState: NonSelectableDayState) {
  Text(
    text = dayState.date.dayOfMonth.toString(),
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.h6,
  )
}

@Composable
private fun WeekHeader(daysOfWeek: List<DayOfWeek>) {
  Row {
    daysOfWeek.forEach { dayOfWeek ->
      Text(
        textAlign = TextAlign.Center,
        text = dayOfWeek.getDisplayName(NARROW, Locale.ROOT),
        modifier = Modifier
          .weight(1f)
          .wrapContentHeight()
      )
    }
  }
}

@Composable
private fun MonthHeader(currentState: CurrentState) {
  Row {
    Text(currentState.day.year.toString(), style = MaterialTheme.typography.h3)
    Text(currentState.day.month.name, style = MaterialTheme.typography.h3)
    IconButton(onClick = { currentState.day = currentState.day.plusMonths(1) }) {
      Image(
        imageVector = Icons.Default.Star,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Next",
      )
    }
  }
}

@Composable
private fun MonthContainer(content: @Composable (PaddingValues) -> Unit) {
  Card(
    elevation = 0.dp,
    shape = RoundedCornerShape(10.dp),
    border = BorderStroke(1.dp, Color.LightGray),
    content = { content(PaddingValues(4.dp)) },
  )
}

@Preview
@Composable
private fun CustomComponentsPreview() {
  MaterialTheme {
    Surface {
      CustomComponentsSample()
    }
  }
}
