package com.github.boguszpawlowski.composecalendar.day

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
import com.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.LocalDate

@Composable
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
