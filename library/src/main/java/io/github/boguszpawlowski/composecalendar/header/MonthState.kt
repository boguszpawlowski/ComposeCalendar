package io.github.boguszpawlowski.composecalendar.header

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun MonthState(
  initialMonth: YearMonth,
  minMonth: YearMonth,
  maxMonth: YearMonth,
): MonthState = MonthStateImpl(initialMonth, minMonth, maxMonth)

@Stable
public interface MonthState {
  public var currentMonth: YearMonth
  public var minMonth: YearMonth
  public var maxMonth: YearMonth

  public companion object {
    public fun Saver(): Saver<MonthState, Any> = mapSaver(
      save = { monthState ->
        mapOf(
          "currentMonth" to monthState.currentMonth.toString(),
          "minMonth" to monthState.minMonth.toString(),
          "maxMonth" to monthState.maxMonth.toString(),
        )
      },
      restore = { restoreMap ->
        MonthState(
          YearMonth.parse(restoreMap["currentMonth"] as String),
          YearMonth.parse(restoreMap["minMonth"] as String),
          YearMonth.parse(restoreMap["maxMonth"] as String),
        )
      }
    )
  }
}

@Stable
@SuppressLint("NewApi")
private class MonthStateImpl(
  initialMonth: YearMonth,
  minMonth: YearMonth,
  maxMonth: YearMonth,
) : MonthState {

  private var _currentMonth by mutableStateOf(initialMonth)
  private var _minMonth by mutableStateOf(minMonth)
  private var _maxMonth by mutableStateOf(maxMonth)

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _currentMonth = value
    }

  override var minMonth: YearMonth
    get() = _minMonth
    set(value) {
      if (value > _maxMonth) return
      _minMonth = value
    }

  override var maxMonth: YearMonth
    get() = _maxMonth
    set(value) {
      if (value < _minMonth) return
      _maxMonth = value
    }
}
