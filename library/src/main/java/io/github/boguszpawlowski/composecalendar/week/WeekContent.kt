package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.month.StartIndex
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import io.github.boguszpawlowski.composecalendar.states.EventState
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalSnapperApi::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
internal fun <T : SelectionState> WeekPager(
  initialDay: LocalDate,
  selectionState: T,
  eventState: EventState,
  currentState: CurrentState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  weekContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
  onSwipe: (LocalDate) -> Unit
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
      initialDay = initialDay,
      currentState = currentState,
      listState = listState,
    )
  }

  LaunchedEffect(key1 = listState.firstVisibleItemIndex) {
    onSwipe(weekListState.getWeekForPage(listState.firstVisibleItemIndex))
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
        selectionState = selectionState,
        eventState = eventState,
        currentDay = weekListState.getWeekForPage(index),
        today = today,
        daysOfWeek = daysOfWeek,
        dayContent = dayContent,
        weekDaysNames = weekDaysNames,
        weekContainer = weekContainer
      )
    }
  }
}

@Composable
internal fun <T : SelectionState> WeekContent(
  selectionState: T,
  eventState: EventState,
  currentDay: LocalDate,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  weekContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column {
    Box(
      modifier = modifier
        .wrapContentHeight(),
      content = { weekDaysNames(daysOfWeek) },
    )

    weekContainer { paddingValues ->
      Column(
        modifier = modifier
          .padding(paddingValues)
      ) {
        WeekContainer(
          week = currentDay.getWeek(
            today = today,
          ),
          selectionState = selectionState,
          eventState = eventState,
          dayContent = dayContent,
        )
      }
    }
  }
}

@Composable
internal fun <T : SelectionState> WeekContainer(
  week: Week,
  selectionState: T,
  eventState: EventState,
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
        dayContent(DayState(day, selectionState, eventState))
      }
    }
  }
}

@OptIn(ExperimentalSnapperApi::class)
private val coerceSnapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int =
  { _, startIndex, targetIndex ->
    targetIndex
      .coerceIn(startIndex - 1, startIndex + 1)
  }
