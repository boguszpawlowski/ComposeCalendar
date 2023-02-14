package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import io.github.boguszpawlowski.composecalendar.states.EventState

@Composable
fun StaticCalendarSample() {
  val scrollState = rememberScrollState()
  Column(modifier = Modifier
    .fillMaxWidth()
    .verticalScroll(scrollState)
  ) {
    val calendarState = rememberCalendarState(
      eventState = EventState( dayEventList )
    )
    ModeControls(modeState = calendarState.modeState)
    StaticCalendar(
      modifier = Modifier
        .padding(8.dp)
        .animateContentSize(),
      calendarState = calendarState
    )
  }
}
