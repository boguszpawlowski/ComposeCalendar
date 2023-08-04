package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.day.WeekDay
import io.github.boguszpawlowski.composecalendar.header.WeekState
import io.github.boguszpawlowski.composecalendar.month.PagerItemCount
import io.github.boguszpawlowski.composecalendar.month.StartIndex
import io.github.boguszpawlowski.composecalendar.month.coerceSnapIndex
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalSnapperApi::class)
@Composable
@Suppress("LongMethod")
internal fun <T : SelectionState> WeekPager(
  initialWeek: Week,
  selectionState: T,
  weekState: WeekState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()

  val listState = rememberLazyListState(
    initialFirstVisibleItemIndex = StartIndex,
  )
  val flingBehavior = rememberSnapperFlingBehavior(
    lazyListState = listState,
    snapOffsetForItem = SnapOffsets.Start,
    springAnimationSpec = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
    decayAnimationSpec = rememberSplineBasedDecay(),
    snapIndex = coerceSnapIndex,
  )

  val weekListState = remember {
    WeekListState(
      coroutineScope = coroutineScope,
      initialWeek = initialWeek,
      weekState = weekState,
      listState = listState,
    )
  }
  Column(
    modifier = modifier,
  ) {
    if (weekDaysScrollEnabled.not()) {
      Box(
        modifier = Modifier
          .wrapContentHeight()
          .fillMaxWidth(),
        content = { daysOfWeekHeader(daysOfWeek) },
      )
    }
    LazyRow(
      modifier = modifier.testTag("WeekPager"),
      state = listState,
      flingBehavior = flingBehavior,
      verticalAlignment = Alignment.Top,
    ) {
      items(PagerItemCount) { index ->
        WeekContent(
          modifier = Modifier.fillParentMaxWidth(),
          daysOfWeek = daysOfWeek,
          weekDays = weekListState.getWeekForPage(index).getWeekDays(today = today),
          selectionState = selectionState,
          weekDaysScrollEnabled = weekDaysScrollEnabled,
          dayContent = dayContent,
          daysOfWeekHeader = daysOfWeekHeader,
        )
      }
    }
  }
}

@Composable
internal fun <T : SelectionState> WeekContent(
  selectionState: T,
  weekDays: WeekDays,
  daysOfWeek: List<DayOfWeek>,
  modifier: Modifier = Modifier,
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  daysOfWeekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
) {
  Column(
    modifier = modifier,
  ) {
    if (weekDaysScrollEnabled) {
      Box(
        modifier = Modifier
          .wrapContentHeight()
          .fillMaxWidth(),
        content = { daysOfWeekHeader(daysOfWeek) },
      )
    }
    WeekRow(
      weekDays = weekDays,
      modifier = Modifier.fillMaxWidth(),
      selectionState = selectionState,
      dayContent = dayContent,
    )
  }
}

internal fun Week.getWeekDays(today: LocalDate): WeekDays {
  val weekDays = days.map {
    WeekDay(
      date = it,
      isCurrentDay = it == today,
      isFromCurrentMonth = true,
    )
  }

  return WeekDays(
    isFirstWeekOfTheMonth = false,
    days = weekDays,
  )
}
