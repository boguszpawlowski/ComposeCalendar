package com.bpawlowski.composecalendar.selection

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
sealed interface DateSelection {
  val start: LocalDate

  data class Day(override val start: LocalDate) : DateSelection
  data class Period(
    override val start: LocalDate,
    val end: LocalDate,
  ) : DateSelection
}
