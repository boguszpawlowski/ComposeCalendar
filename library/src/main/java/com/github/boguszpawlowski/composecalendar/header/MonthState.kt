package com.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun MonthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@Stable
public interface MonthState {
  public var currentMonth: YearMonth

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

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _currentMonth = value
    }
}
