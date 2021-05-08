package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public sealed interface SelectionValue {

  public object None : SelectionValue
  public data class Single(val selection: LocalDate) : SelectionValue
  public data class Multiple(val selection: Collection<LocalDate>) : SelectionValue
  public data class Period(
    val start: LocalDate,
    val end: LocalDate? = null,
  ) : SelectionValue {
    public fun contains(date: LocalDate): Boolean =
      date == start || date == end || date.isAfter(start) && date.isBefore(end ?: LocalDate.MIN)
  }

  public fun isDateSelected(date: LocalDate): Boolean = when (this) {
    None -> false
    is Multiple -> selection.contains(date)
    is Single -> this.selection == date
    is Period -> contains(date)
  }
}
