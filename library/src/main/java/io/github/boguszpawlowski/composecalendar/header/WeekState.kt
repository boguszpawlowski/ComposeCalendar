package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.LocalDate

@Suppress("FunctionName") // Factory function
public fun WeekState(initialWeek: Week): WeekState = WeekStateImpl(initialWeek)

@Stable
public interface WeekState {
  public var currentWeek: Week

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<WeekState, String> = Saver(
      save = { it.currentWeek.start.toString() },
      restore = { WeekState(initialWeek = Week(firstDay = LocalDate.parse(it))) }
    )
  }
}

@Stable
private class WeekStateImpl(
  initialWeek: Week,
) : WeekState {

  private var _currentWeek by mutableStateOf<Week>(initialWeek)

  override var currentWeek: Week
    get() = _currentWeek
    set(value) {
      _currentWeek = value
    }
}
