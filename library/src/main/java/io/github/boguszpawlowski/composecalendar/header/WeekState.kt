package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun WeekState(initialFirstDayOfWeek: LocalDate): WeekState = WeekStateImpl(initialFirstDayOfWeek)

@Stable
public interface WeekState {
  public var firstDayOfWeek: LocalDate

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<WeekState, String> = Saver(
      save = { it.toString() },
      restore = { WeekState(LocalDate.parse(it)) }
    )
  }
}

@Stable
private class WeekStateImpl(
  initialFirstDayOfWeek: LocalDate,
) : WeekState {

  private var _firstDayOfWeek by mutableStateOf<LocalDate>(initialFirstDayOfWeek)

  override var firstDayOfWeek: LocalDate
    get() = _firstDayOfWeek
    set(value) {
      _firstDayOfWeek = value
    }
}