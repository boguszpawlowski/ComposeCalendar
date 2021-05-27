package com.bpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayState
import com.bpawlowski.composecalendar.selection.SelectionState

@Composable
internal fun WeekContent(
  week: Week,
  selectionState: SelectionState,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState) -> Unit
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
