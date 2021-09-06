package io.github.boguszpawlowski.composecalendar.month

import android.annotation.SuppressLint
import androidx.annotation.IntRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.util.dec
import io.github.boguszpawlowski.composecalendar.util.inc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.runningFold
import java.time.YearMonth

private const val PageCount = 3

@OptIn(ExperimentalPagerApi::class)
internal class MonthPagerState(
  coroutineScope: CoroutineScope,
  private val monthState: MonthState,
  private val pagerState: PagerState,
) {

  private var monthProvider by mutableStateOf(
    MonthProvider(
      initialMonth = monthState.currentMonth,
      currentIndex = pagerState.currentPage,
    )
  )

  fun getMonthForIndex(@IntRange(from = 0, to = 2) index: Int) = monthProvider.cache[index]!!

  init {
    snapshotFlow { monthState.currentMonth }.onEach { month ->
      moveToMonth(month)
    }.launchIn(coroutineScope)

    snapshotFlow { pagerState.currentPage }.runningFold(1 to 1) { oldIndices, newIndex ->
      oldIndices.second to newIndex
    }.distinctUntilChanged().onEach { (oldIndex, newIndex) ->
      onScrolled(oldIndex, newIndex)
      monthState.currentMonth = getMonthForIndex(newIndex)
    }.launchIn(coroutineScope)
  }

  @SuppressLint("Range")
  private fun moveToMonth(month: YearMonth) {
    if (month - getMonthForIndex(pagerState.currentPage) == 0) return
    monthProvider = MonthProvider(monthState.currentMonth, pagerState.currentPage)
  }

  private fun onScrolled(oldIndex: Int, newIndex: Int) {
    when (oldIndex - newIndex) {
      -1, PageCount - 1 -> monthProvider.cache[(newIndex + 1) % PageCount] =
        monthProvider.cache[newIndex]!!.inc()
      else -> monthProvider.cache[(newIndex - 1 + PageCount) % PageCount] =
        monthProvider.cache[newIndex]!!.dec()
    }
  }
}

internal class MonthProvider(initialMonth: YearMonth, currentIndex: Int) {
  val cache = mutableStateMapOf<Int, YearMonth>()

  init {
    cache[(currentIndex - 1 + PageCount) % PageCount] = initialMonth.dec()
    cache[currentIndex] = initialMonth
    cache[(currentIndex + 1) % PageCount] = initialMonth.inc()
  }
}

private operator fun YearMonth.minus(other: YearMonth) =
  year - other.year + month.value - other.month.value
