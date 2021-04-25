package com.bpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionNaming") // Factory function
public fun MonthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@Stable
public interface MonthState {
  public val currentMonth: YearMonth
  public fun onMonthChanged(newMonth: YearMonth)
}

@Stable
private class MonthStateImpl(
  initialMonth: YearMonth,
) : MonthState {

  private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

  override val currentMonth: YearMonth
    get() = _currentMonth

  override fun onMonthChanged(newMonth: YearMonth) {
    _currentMonth = newMonth
  }
}
