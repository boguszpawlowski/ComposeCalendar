package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.time.DayOfWeek
import java.time.format.TextStyle.SHORT
import java.util.Locale

@Composable
public fun DefaultWeekHeader(
  daysOfWeek: List<DayOfWeek>,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier) {
    daysOfWeek.forEach { dayOfWeek ->
      Text(
        textAlign = TextAlign.Center,
        text = dayOfWeek.getDisplayName(SHORT, Locale.getDefault()),
        modifier = modifier
          .weight(1f)
          .wrapContentHeight()
      )
    }
  }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
