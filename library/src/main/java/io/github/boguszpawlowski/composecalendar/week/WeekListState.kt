package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import io.github.boguszpawlowski.composecalendar.header.WeekState
import io.github.boguszpawlowski.composecalendar.util.throttleOnOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit

@Stable
internal class WeekListState(
  private val coroutineScope: CoroutineScope,
  private val weekState: WeekState,
  private val listState: LazyListState,
) {

  private val currentlyFirstVisibleMonth by derivedStateOf {
    getWeekForPage(listState.firstVisibleItemIndex)
  }

  init {
    snapshotFlow { weekState.currentWeek }.onEach { week ->
      moveToWeek(week)
    }.launchIn(coroutineScope)

    with(listState) {
      snapshotFlow { currentlyFirstVisibleMonth }
        .throttleOnOffset()
        .onEach { newMonth ->
          weekState.currentWeek = newMonth
        }.launchIn(coroutineScope)
    }
  }

  fun getWeekForPage(index: Int): Week =
    weekState.minWeek.plusWeeks(index.toLong())

  private fun moveToWeek(week: Week) {
    if (week == currentlyFirstVisibleMonth) return
    coroutineScope.launch {
      listState.animateScrollToItem(ChronoUnit.WEEKS.between(weekState.minWeek, week))
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WeekListState

    if (weekState != other.weekState) return false
    if (listState != other.listState) return false

    return true
  }

  override fun hashCode(): Int {
    var result = weekState.hashCode()
    result = 31 * result + listState.hashCode()
    return result
  }
}

private operator fun Week.minus(other: Week) =
  ChronoUnit.WEEKS.between(other.start, this.start)
