package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle.FULL
import java.util.Locale

/**
 * Default implementation of month header, shows current month and year, as well as
 * 2 arrows for changing currently showed month
 */
@Composable
public fun DefaultWeekHeader(
  weekState: WeekState,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(
      modifier = Modifier.testTag("Decrement"),
      onClick = { weekState.currentWeek = weekState.currentWeek.dec() }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowLeft,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Previous",
      )
    }
    Text(
      modifier = Modifier.testTag("WeekLabel"),
      text = weekState.currentWeek.toMonthLabel(),
      style = MaterialTheme.typography.h4,
    )
    IconButton(
      modifier = Modifier.testTag("Increment"),
      onClick = { weekState.currentWeek = weekState.currentWeek.inc() }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowRight,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Next",
      )
    }
  }
}

private fun Week.toMonthLabel() = when (months.size) {
  1 -> months.first().toLabel()
  2 -> months.first().month.toLabel() + "..." + months[1].toLabel()
  else -> error("Invalid number of months per week")
}

private fun YearMonth.toLabel(): String = month.toLabel() + " " + year.toString()

private fun Month.toLabel(): String =
  getDisplayName(FULL, Locale.getDefault())
    .lowercase()
    .replaceFirstChar { it.titlecase() }
