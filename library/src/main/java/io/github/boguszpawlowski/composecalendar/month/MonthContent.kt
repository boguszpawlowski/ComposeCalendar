package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.week.WeekContent
import io.github.boguszpawlowski.composecalendar.week.getWeeks
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysOfWeek = 7

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> MonthPager(
  showAdjacentMonths: Boolean,
  selectionState: T,
  monthState: MonthState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  val pagerState = rememberPagerState(pageCount = 3, initialPage = 1, infiniteLoop = true)
  val coroutineScope = rememberCoroutineScope()

  val monthPagerState = remember {
    MonthPagerState(
      coroutineScope = coroutineScope,
      monthState = monthState,
      pagerState = pagerState,
    )
  }

  HorizontalPager(
    modifier = modifier.testTag("MonthPager"),
    state = pagerState,
    verticalAlignment = Alignment.Top,
  ) { pageIndex ->
    MonthContent(
      showAdjacentMonths = showAdjacentMonths,
      selectionState = selectionState,
      currentMonth = monthPagerState.getMonthForIndex(pageIndex),
      today = today,
      daysOfWeek = daysOfWeek,
      dayContent = dayContent,
      weekHeader = weekHeader,
      monthContainer = monthContainer
    )
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  currentMonth: YearMonth,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      content = { weekHeader(daysOfWeek) },
    )

    monthContainer { paddingValues ->
      Column(
        modifier = Modifier
          .wrapContentWidth()
          .padding(paddingValues)
      ) {
        currentMonth.getWeeks(
          includeAdjacentMonths = showAdjacentMonths,
          firstDayOfTheWeek = daysOfWeek.first(),
          today = today,
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
}
