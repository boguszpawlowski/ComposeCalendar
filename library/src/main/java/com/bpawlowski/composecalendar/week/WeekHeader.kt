package com.bpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.format.TextStyle.SHORT
import java.util.Locale

@Composable
public fun WeekHeader(modifier: Modifier = Modifier) {
  Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier) {
    DayOfWeek.values().forEachIndexed { index, dayOfWeek ->
      Text(
        textAlign = TextAlign.Center,
        text = dayOfWeek.getDisplayName(SHORT, Locale.ROOT),
        modifier = Modifier
          .fillMaxWidth(1f / (7 - index))
          .height(30.dp)
      )
    }
  }
}
