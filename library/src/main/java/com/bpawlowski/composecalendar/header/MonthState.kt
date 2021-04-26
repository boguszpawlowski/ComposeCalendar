package com.bpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionNaming") // Factory function
public fun MonthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@Stable
public interface MonthState {
  public val currentMonth: YearMonth
  public fun onMonthChanged(newMonth: YearMonth)

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<MonthState, String> = Saver(
      save = { it.currentMonth.toString() },
      restore = { MonthState(YearMonth.parse(it)) }
    )
  }
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
