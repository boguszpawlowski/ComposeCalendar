package com.bpawlowski.composecalendar.week

import androidx.compose.foundation.layout.RowScope
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
public fun RowScope.DefaultWeekHeader(modifier: Modifier = Modifier) {
  DayOfWeek.values().forEach { dayOfWeek ->
    Text(
      textAlign = TextAlign.Center,
      text = dayOfWeek.getDisplayName(SHORT, Locale.ROOT),
      modifier = modifier
        .weight(1f)
        .height(30.dp)
    )
  }
}
