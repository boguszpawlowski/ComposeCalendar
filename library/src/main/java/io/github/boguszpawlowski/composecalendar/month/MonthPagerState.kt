package io.github.boguszpawlowski.composecalendar.month

import android.annotation.SuppressLint
import androidx.annotation.IntRange
import androidx.compose.runtime.Stable
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
@Stable
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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MonthPagerState

    if (monthState != other.monthState) return false
    if (pagerState != other.pagerState) return false
    if (monthProvider != other.monthProvider) return false

    return true
  }

  override fun hashCode(): Int {
    var result = monthState.hashCode()
    result = 31 * result + pagerState.hashCode()
    result = 31 * result + monthProvider.hashCode()
    return result
  }
}

@Stable
internal class MonthProvider(initialMonth: YearMonth, currentIndex: Int) {
  val cache = mutableStateMapOf<Int, YearMonth>()

  init {
    cache[(currentIndex - 1 + PageCount) % PageCount] = initialMonth.dec()
    cache[currentIndex] = initialMonth
    cache[(currentIndex + 1) % PageCount] = initialMonth.inc()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MonthProvider

    if (cache != other.cache) return false

    return true
  }

  override fun hashCode(): Int {
    return cache.hashCode()
  }
}

private operator fun YearMonth.minus(other: YearMonth) =
  year - other.year + month.value - other.month.value
