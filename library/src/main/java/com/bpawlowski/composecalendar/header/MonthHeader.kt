package com.bpawlowski.composecalendar.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
public fun MonthHeader(
  monthState: MonthState,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
  ) {
    IconButton(onClick = { monthState.onMonthChanged(monthState.currentMonth.plusMonths(-1)) }) {
      Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Next")
    }
    Text(
      text = monthState.currentMonth.month.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT),
      style = MaterialTheme.typography.h4
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h4)
    IconButton(onClick = { monthState.onMonthChanged(monthState.currentMonth.plusMonths(1)) }) {
      Image(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next")
    }
  }
}
