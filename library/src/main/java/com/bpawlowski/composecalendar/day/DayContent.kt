package com.bpawlowski.composecalendar.day

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
public fun DayContent(
  day: Day,
  modifier: Modifier = Modifier,
) {

  Card(
    modifier = modifier.padding(2.dp),
    elevation = if (day.isFromCurrentMonth) 4.dp else 0.dp,
    border = if (day.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colors.secondary) else null,
  ) {
    Row(
      modifier = Modifier.clickable { },
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = day.date.dayOfMonth.toString())
    }
  }
}

@Preview
@Composable
private fun DayPreview() {
  val date = LocalDate.now()
  val state = Day(date)
  DayContent(state)
}
