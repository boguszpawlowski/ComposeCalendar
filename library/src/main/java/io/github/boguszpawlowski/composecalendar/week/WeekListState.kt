package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Stable
internal class WeekListState(
  private val coroutineScope: CoroutineScope,
  private val initialDay: LocalDate,
  private val currentState: CurrentState,
  private val listState: LazyListState,
) {

  private val currentlyVisibleWeek by derivedStateOf {
    getWeekForPage(listState.firstVisibleItemIndex)
  }

  init {
    snapshotFlow { currentState.day }.onEach { day ->
      moveToWeek(day)
    }.launchIn(coroutineScope)

    snapshotFlow { currentlyVisibleWeek }.onEach { newDay ->
      currentState.day = newDay
    }.launchIn(coroutineScope)
  }

  fun getWeekForPage(index: Int): LocalDate =
    initialDay.plusDays((index - StartIndex).toLong() * 7)

  private fun moveToWeek(day: LocalDate) {
    if (day.toEpochDay() >= currentlyVisibleWeek.toEpochDay() &&
      day.toEpochDay() < currentlyVisibleWeek.toEpochDay() + 7) return
    initialDay.minus(day).let { offset ->
      coroutineScope.launch {
        listState.animateScrollToItem((StartIndex - offset).toInt())
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WeekListState

    if (currentState != other.currentState) return false
    if (listState != other.listState) return false

    return true
  }

  override fun hashCode(): Int {
    var result = currentState.hashCode()
    result = 7 * result + listState.hashCode()
    return result
  }
}

private operator fun LocalDate.minus(other: LocalDate) =
  ChronoUnit.WEEKS.between(other, this)

internal const val PagerItemCount = 20_000
internal const val StartIndex = PagerItemCount / 2