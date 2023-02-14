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
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import java.time.format.TextStyle.FULL
import java.util.Locale

/**
 * Default implementation of month header, shows current month and year, as well as
 * 2 arrows for changing currently showed month
 */
@Composable
public fun DefaultMonthHeader(
  currentState: CurrentState,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    IconButton(
      modifier = Modifier.testTag("Decrement"),
      onClick = { currentState.day = currentState.day.minusMonths(1L) }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowLeft,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Previous",
      )
    }
    Text(
      modifier = Modifier.testTag("MonthLabel"),
      text = currentState.day.month
        .getDisplayName(FULL, Locale.getDefault())
        .lowercase()
        .replaceFirstChar { it.titlecase() },
      style = MaterialTheme.typography.h5,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(text = currentState.day.year.toString(), style = MaterialTheme.typography.h5)
    IconButton(
      modifier = Modifier.testTag("Increment"),
      onClick = { currentState.day = currentState.day.plusMonths(1L) }
    ) {
      Image(
        imageVector = Icons.Default.KeyboardArrowRight,
        colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
        contentDescription = "Next",
      )
    }
  }
}
