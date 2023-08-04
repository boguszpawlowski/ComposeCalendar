package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.WeekRow
import io.github.boguszpawlowski.composecalendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalSnapperApi::class)
@Composable
@Suppress("LongMethod")
internal fun <T : SelectionState> MonthPager(
  initialMonth: YearMonth,
  showAdjacentMonths: Boolean,
  selectionState: T,
  monthState: MonthState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
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
      monthState = monthState,
      listState = listState,
    )
  }

  Column(modifier = Modifier.fillMaxWidth()) {
    if (weekDaysScrollEnabled.not()) {
      Box(
        modifier = Modifier
          .wrapContentHeight()
          .fillMaxWidth(),
        content = { weekHeader(daysOfWeek) },
      )
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
          currentMonth = monthListState.getMonthForPage(index),
          today = today,
          weekDaysScrollEnabled = weekDaysScrollEnabled,
          daysOfWeek = daysOfWeek,
          dayContent = dayContent,
          weekHeader = weekHeader,
          monthContainer = monthContainer
        )
      }
    }
  }
}

@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  currentMonth: YearMonth,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  weekDaysScrollEnabled: Boolean = true,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column {
    if (weekDaysScrollEnabled) {
      Box(
        modifier = modifier
          .wrapContentHeight(),
        content = { weekHeader(daysOfWeek) },
      )
    }
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
          WeekRow(
            weekDays = week,
            selectionState = selectionState,
            dayContent = dayContent,
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalSnapperApi::class)
internal val coerceSnapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int =
  { _, startIndex, targetIndex ->
    targetIndex
      .coerceIn(startIndex - 1, startIndex + 1)
  }
