package io.github.boguszpawlowski.composecalendar.header

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import java.time.format.TextStyle.FULL
import java.util.Locale

/**
 * Default implementation of week header, shows current month and year, as well as
 * 2 arrows for changing currently showed month
 */
@Composable
@Suppress("LongMethod")
public fun DefaultWeekHeader(
  weekState: WeekState,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    DecrementButton(weekState = weekState)
    Text(
      modifier = Modifier.testTag("WeekLabel"),
      text = weekState.currentWeek.yearMonth.month
        .getDisplayName(FULL, Locale.getDefault())
        .lowercase()
        .replaceFirstChar { it.titlecase() },
      style = MaterialTheme.typography.h4,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = weekState.currentWeek.yearMonth.year.toString(),
      style = MaterialTheme.typography.h4
    )
    IncrementButton(monthState = weekState)
  }
}

@Composable
private fun DecrementButton(
  weekState: WeekState,
) {
  IconButton(
    modifier = Modifier.testTag("Decrement"),
    enabled = weekState.currentWeek > weekState.minWeek,
    onClick = { weekState.currentWeek = weekState.currentWeek.dec() }
  ) {
    Image(
      imageVector = Icons.Default.KeyboardArrowLeft,
      colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
      contentDescription = "Previous",
    )
  }
}

@Composable
private fun IncrementButton(
  monthState: WeekState,
) {
  IconButton(
    modifier = Modifier.testTag("Increment"),
    enabled = monthState.currentWeek < monthState.maxWeek,
    onClick = { monthState.currentWeek = monthState.currentWeek.inc() }
  ) {
    Image(
      imageVector = Icons.Default.KeyboardArrowRight,
      colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
      contentDescription = "Next",
    )
  }
}
