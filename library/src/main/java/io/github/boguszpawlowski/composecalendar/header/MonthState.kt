package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import io.github.boguszpawlowski.composecalendar.util.dec
import io.github.boguszpawlowski.composecalendar.util.inc
import java.time.LocalDate
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun MonthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@Stable
public interface MonthState {
  public var currentMonth: YearMonth

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<MonthState, String> = Saver(
      save = { it.currentMonth.toString() },
      restore = { MonthState(YearMonth.parse(it)) }
    )
  }
}

@Stable
private class MonthStateImpl(
  initialMonth: YearMonth,
) : MonthState {

  private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _currentMonth = value
    }
}

public sealed interface CurrentDatePeriod {

  public fun inc(): CurrentDatePeriod
  public fun dec(): CurrentDatePeriod

  @JvmInline
  public value class Month(public val date: YearMonth) : CurrentDatePeriod {
    override fun inc(): Month = Month(date.inc())

    override fun dec(): Month = Month(date.dec())
  }

  @JvmInline
  public value class Week(public val days: List<LocalDate>) : CurrentDatePeriod {
    init {
      check(days.size == 7)
    }

    override fun inc(): Week = Week(days = days.map { it.plusDays(7) })

    override fun dec(): Week = Week(days = days.map { it.minusDays(7) })
  }
}
