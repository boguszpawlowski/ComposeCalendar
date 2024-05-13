package io.github.boguszpawlowski.composecalendar.day

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

/**
 * Default implementation for day content. It supports different appearance for days from
 * current and adjacent month, as well as current day and selected day
 *
 * @param selectionColor color of the border, when day is selected
 * @param currentDayColor color of content for the current date
 * @param onClick callback for interacting with day clicks
 */
@Composable
@SuppressLint("NewApi")
public fun <T : SelectionState> DefaultDay(
  state: DayState<T>,
  modifier: Modifier = Modifier,
  selectionColor: Color = MaterialTheme.colors.secondary,
  currentDayColor: Color = MaterialTheme.colors.primary,
  onClick: (LocalDate) -> Unit = {},
) {
  val date = state.date
  val selectionState = state.selectionState

  val isSelected = selectionState.isDateSelected(date)

  Card(
    modifier = modifier
      .aspectRatio(1f)
      .padding(2.dp),
    elevation = if (state.isFromCurrentMonth) 4.dp else 0.dp,
    border = if (state.isCurrentDay) BorderStroke(1.dp, currentDayColor) else null,
    contentColor = if (isSelected) selectionColor else contentColorFor(
      backgroundColor = MaterialTheme.colors.surface
    )
  ) {
    Box(
      modifier = Modifier.clickable {
        onClick(date)
        selectionState.onDateSelected(date)
      },
      contentAlignment = Alignment.Center,
    ) {
      Text(text = date.dayOfMonth.toString())
    }
  }
}
