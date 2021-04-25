package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Stable
import java.time.LocalDate

@Stable
public sealed interface SelectionValue {

  public object None : SelectionValue
  public data class Single(val date: LocalDate) : SelectionValue
  public data class Multiple(val selection: Collection<LocalDate>) : SelectionValue
  public data class Period(
    val start: LocalDate,
    val end: LocalDate? = null,
  ) : SelectionValue {
    public fun contains(date: LocalDate): Boolean =
      date == start || date == end || (date.isAfter(start) && date.isBefore(end ?: LocalDate.MIN))
  }
}
