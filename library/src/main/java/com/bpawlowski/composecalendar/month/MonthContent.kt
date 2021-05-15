package com.bpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayState
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.week.WeekContent
import com.bpawlowski.composecalendar.week.getWeeks
import com.bpawlowski.composecalendar.week.rotateRight
import java.time.DayOfWeek

internal const val DaysOfWeek = 7

@Composable
internal fun MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: SelectionState,
  month: Month,
  firstDayOfWeek: DayOfWeek,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable () -> Unit) -> Unit,
) {

  val daysOfWeek = remember(firstDayOfWeek) {
    DayOfWeek.values().rotateRight(DaysOfWeek - firstDayOfWeek.ordinal)
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
    content = { weekHeader(daysOfWeek) },
  )

  monthContainer {
    Column {
      month.yearMonth.getWeeks(
        includeAdjacentMonths = showAdjacentMonths,
        firstDayOfTheWeek = firstDayOfWeek,
      ).forEach { week ->
        WeekContent(
          week = week,
          selectionState = selectionState,
          dayContent = dayContent,
        )
      }
    }
  }
}
