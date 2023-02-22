package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun WeekState(initialMonth: YearMonth): WeekState = WeekStateImpl(initialMonth)

@Stable
public interface WeekState {
  public var currentMonth: YearMonth

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<WeekState, String> = Saver(
      save = { it.currentMonth.toString() },
      restore = { WeekState(YearMonth.parse(it)) }
    )
  }
}

@Stable
private class WeekStateImpl(
  initialMonth: YearMonth,
) : WeekState {

  private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _currentMonth = value
    }
}
