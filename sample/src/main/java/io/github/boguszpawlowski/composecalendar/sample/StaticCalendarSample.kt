package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.StaticCalendar

@Composable
fun StaticCalendarSample() {
  StaticCalendar(
    modifier = Modifier
      .padding(8.dp)
      .animateContentSize(),
  )
}
