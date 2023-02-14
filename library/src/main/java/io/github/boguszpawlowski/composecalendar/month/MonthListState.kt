package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.states.CurrentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@Stable
internal class MonthListState(
  private val coroutineScope: CoroutineScope,
  private val initialMonth: YearMonth,
  private val currentState: CurrentState,
  private val listState: LazyListState,
  private val selectionState: DynamicSelectionState?
) {

  private val currentlyVisibleMonth by derivedStateOf {
    getMonthForPage(listState.firstVisibleItemIndex)
  }

  init {
    snapshotFlow { YearMonth.of(
      currentState.day.year,
      currentState.day.month
    ) }.onEach { month ->
      moveToMonth(month)
    }.launchIn(coroutineScope)

    snapshotFlow { currentlyVisibleMonth }.onEach { newMonth ->
      currentState.day = if (selectionState != null && selectionState.selection.isNotEmpty())
        if (selectionState.selection[0].month == newMonth.month) newMonth.atDay(selectionState.selection[0].dayOfMonth)
          else newMonth.atDay(1)
      else newMonth.atDay(1)
    }.launchIn(coroutineScope)
  }

  fun getMonthForPage(index: Int): YearMonth =
    initialMonth.plusMonths((index - StartIndex).toLong())

  private fun moveToMonth(month: YearMonth) {
    if (month == currentlyVisibleMonth) return
    initialMonth.minus(month).let { offset ->
      coroutineScope.launch {
        listState.animateScrollToItem((StartIndex - offset).toInt())
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MonthListState

    if (currentState != other.currentState) return false
    if (listState != other.listState) return false

    return true
  }

  override fun hashCode(): Int {
    var result = currentState.hashCode()
    result = 31 * result + listState.hashCode()
    return result
  }
}

private operator fun YearMonth.minus(other: YearMonth) =
  ChronoUnit.MONTHS.between(other, this)

internal const val PagerItemCount = 20_000
internal const val StartIndex = PagerItemCount / 2
