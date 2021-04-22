package com.bpawlowski.composecalendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import java.time.YearMonth
import java.util.Locale

@Composable
fun HeaderContent(
  header: Header,
  onCurrentMonthChanged: (YearMonth) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
  ) {
    IconButton(onClick = { onCurrentMonthChanged(header.month.plusMonths(-1)) }) {
      Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Next")
    }
    Text(
      text = header.month.month.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT),
      style = MaterialTheme.typography.h4
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = header.month.year.toString(), style = MaterialTheme.typography.h4)
    IconButton(onClick = { onCurrentMonthChanged(header.month.plusMonths(1)) }) {
      Image(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next")
    }
  }
}
