package io.github.boguszpawlowski.composecalendar.states

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate

@Stable
public data class DayEvent(
  val day: LocalDate,
  val eventCount: Int
)

@Stable
public class EventState(
  initialEventList: List<DayEvent>
) {
  private var _eventList by mutableStateOf(initialEventList)

  public var eventList: List<DayEvent>
    get() = _eventList
    set(value) {
      if (value != eventList) {
        _eventList = value
      }
    }

  public fun getEventsByDate(date: LocalDate): Int = eventList.firstOrNull{ it.day == date }?.eventCount ?: 0

  public companion object {
    @Suppress("FunctionName", "UNCHECKED_CAST")
    // Factory function
    public fun Saver(): Saver<EventState, Any> = Saver(
        save = { row ->
          row.eventList.map { "${it.day}\n${it.eventCount}" }
        },
        restore = { restored ->
          EventState(
            initialEventList = (restored as? List<String>)?.map {
              DayEvent(
                day = LocalDate.parse(it.lines()[0]),
                eventCount = it.lines()[1].toIntOrNull()?: 0
              )
            }.orEmpty()
          )
        }
      )
  }
}






//@Suppress("FunctionName") // Factory function
//public fun EventState(initialEventList: List<DayEvent>): EventState = EventStateImpl(initialEventList)
//
//@Stable
//public interface EventState {
//  public var eventList: List<DayEvent>
//
//  public companion object {
//    @Suppress("FunctionName") // Factory function
//    public fun Saver(): Saver<EventState, String> = Saver(
//      save = { it.toString() },
//      restore = { EventState(emptyList()) } // correct it in future
//    )
//  }
//}
//
//@Stable
//private class EventStateImpl(
//  initialEventList: List<DayEvent>,
//) : EventState {
//
//  private val _currentEventList = mutableStateListOf<DayEvent>()
//
//  init {
//    _currentEventList.clear()
//    _currentEventList.addAll(initialEventList)
//  }
//
//  override var eventList: List<DayEvent>
//    get() = _currentEventList
//    set(value) {
//      _currentEventList.clear()
//      _currentEventList.addAll(value)
//    }
//}
//