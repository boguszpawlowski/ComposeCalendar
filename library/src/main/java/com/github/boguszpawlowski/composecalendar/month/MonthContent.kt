package com.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.boguszpawlowski.composecalendar.day.DayState
import com.github.boguszpawlowski.composecalendar.selection.SelectionState
import com.github.boguszpawlowski.composecalendar.week.WeekContent
import com.github.boguszpawlowski.composecalendar.week.getWeeks
import com.github.boguszpawlowski.composecalendar.week.rotateRight
import java.time.DayOfWeek

internal const val DaysOfWeek = 7

@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  month: Month,
  firstDayOfWeek: DayOfWeek,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
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

  monthContainer { paddingValues ->
    Column(
      modifier = Modifier.padding(paddingValues)
    ) {
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
