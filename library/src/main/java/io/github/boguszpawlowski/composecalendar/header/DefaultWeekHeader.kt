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
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import java.time.format.TextStyle.FULL
import java.util.Locale

@Composable
public fun DefaultWeekHeader(
  currentState: CurrentState,
  modifier: Modifier = Modifier,
) {
  val firstDayOfCurrentWeek = currentState.day.minusDays(currentState.day.dayOfWeek.value.toLong() - 1)
  val monthName = firstDayOfCurrentWeek.month
    .getDisplayName(FULL, Locale.getDefault())
    .lowercase()
    .replaceFirstChar { it.titlecase() }
  val nextMonthName = firstDayOfCurrentWeek.plusDays(6L).month
    .getDisplayName(FULL, Locale.getDefault())
    .lowercase()
    .replaceFirstChar { it.titlecase() }
  val monthYearName = if (firstDayOfCurrentWeek.monthValue == firstDayOfCurrentWeek.plusDays(6L).monthValue)
    "$monthName ${currentState.day.year}"
  else "$monthName - $nextMonthName"

  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(
      modifier = Modifier.testTag("Decrement"),
      onClick = { currentState.day = currentState.day.minusDays(7L) }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowLeft,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Previous",
      )
    }
    Text(
      modifier = Modifier.testTag("MonthLabel"),
      text = monthYearName,
      style = MaterialTheme.typography.h5,
    )
    IconButton(
      modifier = Modifier.testTag("Increment"),
      onClick = { currentState.day = currentState.day.plusDays(7L) }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowRight,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Next",
      )
    }
  }
}