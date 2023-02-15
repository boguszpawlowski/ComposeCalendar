package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
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
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import io.github.boguszpawlowski.composecalendar.states.EventState
import io.github.boguszpawlowski.composecalendar.week.WeekContainer
import io.github.boguszpawlowski.composecalendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@OptIn(ExperimentalSnapperApi::class)
@Composable
@Suppress("LongMethod", "LongParameterList")
internal fun <T : SelectionState> MonthPager(
  initialMonth: YearMonth,
  showAdjacentMonths: Boolean,
  selectionState: T,
  eventState: EventState,
  currentState: CurrentState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
  onSwipe: (LocalDate) -> Unit,
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

  val monthListState = remember {
    MonthListState(
      coroutineScope = coroutineScope,
      initialMonth = initialMonth,
      currentState = currentState,
      listState = listState,
      selectionState = if (selectionState is DynamicSelectionState) selectionState
      else null
    )
  }

  LaunchedEffect(key1 = listState.firstVisibleItemIndex) {
    onSwipe(monthListState.getMonthForPage(listState.firstVisibleItemIndex).atDay(1))
  }

  LazyRow(
    modifier = modifier.testTag("MonthPager"),
    state = listState,
    flingBehavior = flingBehavior,
    verticalAlignment = Alignment.Top,
  ) {
    items(PagerItemCount) { index ->
      MonthContent(
        modifier = Modifier.fillParentMaxWidth(),
        showAdjacentMonths = showAdjacentMonths,
        selectionState = selectionState,
        eventState = eventState,
        currentMonth = monthListState.getMonthForPage(index),
        today = today,
        daysOfWeek = daysOfWeek,
        dayContent = dayContent,
        weekDaysNames = weekDaysNames,
        monthContainer = monthContainer
      )
    }
  }
}

@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  eventState: EventState,
  currentMonth: YearMonth,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekDaysNames: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column {
    Box(
      modifier = modifier
        .wrapContentHeight(),
      content = { weekDaysNames(daysOfWeek) },
    )

    monthContainer { paddingValues ->
      Column(
        modifier = modifier
          .padding(paddingValues)
      ) {
        currentMonth.getWeeks(
          includeAdjacentMonths = showAdjacentMonths,
          firstDayOfTheWeek = daysOfWeek.first(),
          today = today,
        ).forEach { week ->
          WeekContainer(
            week = week,
            selectionState = selectionState,
            eventState = eventState,
            dayContent = dayContent,
          )
        }
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
