package com.bpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayContent

@Composable
internal fun WeekContent(
  week: Week,
  modifier: Modifier = Modifier,
) {

  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = if (week.isFirstWeekOfTheMonth) Arrangement.End else Arrangement.Start
  ) {
    week.days.forEachIndexed { index, day ->
      DayContent(
        day = day,
        modifier = Modifier
          .fillMaxWidth(1 / (7f - index))
          .aspectRatio(1f)
      )
    }
  }
}
