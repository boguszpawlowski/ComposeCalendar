package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public sealed interface DateSelection {
  public val start: LocalDate

  public data class Day(override val start: LocalDate) : DateSelection
  public data class Period(
    override val start: LocalDate,
    val end: LocalDate,
  ) : DateSelection
}
