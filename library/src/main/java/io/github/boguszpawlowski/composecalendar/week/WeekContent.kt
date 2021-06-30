package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState

@Composable
internal fun <T : SelectionState> WeekContent(
  week: Week,
  selectionState: T,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    horizontalArrangement = if (week.isFirstWeekOfTheMonth) Arrangement.End else Arrangement.Start
  ) {
    week.days.forEachIndexed { index, day ->
      Box(
        modifier = Modifier.fillMaxWidth(1f / (7 - index))
      ) {
        dayContent(DayState(day, selectionState))
      }
    }
  }
}
