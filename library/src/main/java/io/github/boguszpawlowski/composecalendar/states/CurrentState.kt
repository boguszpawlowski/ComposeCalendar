package io.github.boguszpawlowski.composecalendar.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Suppress("FunctionName") // Factory function
public fun CurrentState(initialDay: LocalDate): CurrentState = CurrentStateImpl(initialDay)

@Stable
public interface CurrentState {
  public var day: LocalDate

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<CurrentState, String> = Saver(
      save = { it.day.toString() },
      restore = { CurrentState(LocalDate.parse(it)) }
    )
  }
}

@Stable
private class CurrentStateImpl(
  initialDay: LocalDate,
) : CurrentState {

  private var _currentDay by mutableStateOf<LocalDate>(initialDay)

  override var day: LocalDate
    get() = _currentDay
    set(value) {
      _currentDay = value
    }
}