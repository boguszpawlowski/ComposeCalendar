package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import org.json.JSONObject
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
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<MonthState, String> = Saver(
      save = { monthState ->
        JSONObject().also { jsonObject ->
          jsonObject.put("currentMonth", monthState.currentMonth.toString())
          jsonObject.put("minMonth", monthState.minMonth.toString())
          jsonObject.put("maxMonth", monthState.maxMonth.toString())
        }.toString()
      },
      restore = {
        val jsonObject = JSONObject(it)
        MonthState(
          YearMonth.parse(jsonObject.getString("currentMonth")),
          YearMonth.parse(jsonObject.getString("minMonth")),
          YearMonth.parse(jsonObject.getString("maxMonth")),
        )
      }
    )
  }
}

@Stable
private class MonthStateImpl(
  initialMonth: YearMonth,
  minMonth: YearMonth,
  maxMonth: YearMonth,
) : MonthState {

  private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)
  private var _minMonth by mutableStateOf<YearMonth>(minMonth)
  private var _maxMonth by mutableStateOf<YearMonth>(maxMonth)

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _currentMonth = value
    }

  override var minMonth: YearMonth
    get() = _minMonth
    set(value) {
      _minMonth = value
    }

  override var maxMonth: YearMonth
    get() = _maxMonth
    set(value) {
      _maxMonth = value
    }
}
