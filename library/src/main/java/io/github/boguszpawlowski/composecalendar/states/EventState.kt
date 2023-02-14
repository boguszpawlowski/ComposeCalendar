package io.github.boguszpawlowski.composecalendar.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.LocalDate

@Suppress("FunctionName") // Factory function
public fun EventState(initialEventList: List<DayEvent>): EventState = EventStateImpl(initialEventList)

@Stable
public interface EventState {
  public var eventList: List<DayEvent>

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<EventState, String> = Saver(
      save = { it.toString() },
      restore = { EventState(emptyList()) } // correct it in future
    )
  }
}

@Stable
private class EventStateImpl(
  initialEventList: List<DayEvent>,
) : EventState {

  private val _currentEventList = mutableStateListOf<DayEvent>()

  init {
    _currentEventList.addAll(initialEventList)
  }

  override var eventList: List<DayEvent>
    get() = _currentEventList
    set(value) {
      _currentEventList.clear()
      _currentEventList.addAll(value)
    }
}

public data class DayEvent(
  val day: LocalDate,
  val eventCount: Int
)