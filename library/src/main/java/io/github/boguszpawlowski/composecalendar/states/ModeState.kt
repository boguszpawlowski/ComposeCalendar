package io.github.boguszpawlowski.composecalendar.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

@Suppress("FunctionName") // Factory function
public fun ModeState(initialMonthMode: Boolean): ModeState = ModeStateImpl(initialMonthMode)

@Stable
public interface ModeState {
  public var isMonthMode: Boolean

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<ModeState, String> = Saver(
      save = { it.isMonthMode.toString() },
      restore = { ModeState(it.toBoolean()) }
    )
  }
}

@Stable
private class ModeStateImpl(
  initialMonthMode: Boolean,
) : ModeState {

  private var _currentMonthMode by mutableStateOf<Boolean>(initialMonthMode)

  override var isMonthMode: Boolean
    get() = _currentMonthMode
    set(value) {
      _currentMonthMode = value
    }
}