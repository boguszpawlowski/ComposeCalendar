package io.github.boguszpawlowski.composecalendar.header

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import io.github.boguszpawlowski.composecalendar.week.Week
import java.time.LocalDate

@Suppress("FunctionName") // Factory function
public fun WeekState(
  initialWeek: Week,
  minWeek: Week,
  maxWeek: Week,
): WeekState = WeekStateImpl(
  initialWeek = initialWeek,
  minWeek = minWeek,
  maxWeek = maxWeek,
)

@Stable
@SuppressLint("NewApi")
public interface WeekState {
  public var currentWeek: Week
  public var minWeek: Week
  public var maxWeek: Week

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<WeekState, Any> = mapSaver(
      save = { weekState ->
        mapOf(
          CurrentWeekKey to weekState.currentWeek.toString(),
          MinWeekKey to weekState.minWeek.toString(),
          MaxWeekKey to weekState.maxWeek.toString(),
        )
      },
      restore = { restoreMap ->
        WeekState(
          initialWeek = Week(firstDay = LocalDate.parse(restoreMap[CurrentWeekKey] as String)),
          minWeek = Week(firstDay = LocalDate.parse(restoreMap[MinWeekKey] as String)),
          maxWeek = Week(firstDay = LocalDate.parse(restoreMap[MaxWeekKey] as String)),
        )
      }
    )

    private const val CurrentWeekKey = "CurrentWeek"
    private const val MinWeekKey = "MinWeek"
    private const val MaxWeekKey = "MaxWeek"
  }
}

@Stable
private class WeekStateImpl(
  initialWeek: Week,
  minWeek: Week,
  maxWeek: Week,
) : WeekState {

  private var _currentWeek by mutableStateOf(initialWeek)
  private var _minWeek by mutableStateOf(minWeek)
  private var _maxWeek by mutableStateOf(maxWeek)

  override var currentWeek: Week
    get() = _currentWeek
    set(value) {
      _currentWeek = value
    }

  override var minWeek: Week
    get() = _minWeek
    set(value) {
      if (value > _maxWeek) return
      _minWeek = value
    }

  override var maxWeek: Week
    get() = _maxWeek
    set(value) {
      if (value < _minWeek) return
      _maxWeek = value
    }
}
