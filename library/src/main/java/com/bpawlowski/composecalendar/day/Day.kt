package com.bpawlowski.composecalendar.day

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun Day(
  dayState: DayState,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier
      .size(50.dp)
      .padding(2.dp)
      .clickable { },
    elevation = 4.dp,
  ) {
    Text(text = dayState.date.dayOfMonth.toString())
  }
}

@Composable
fun EmptyDay(
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier.size(50.dp).padding(2.dp),)
}

@Preview
@Composable
fun DayPreview() {
  val date = LocalDate.now()
  val state = DayState(date)
  Day(state)
}
