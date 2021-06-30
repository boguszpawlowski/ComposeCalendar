package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.YearMonth

@Immutable
internal data class Month(
  val yearMonth: YearMonth,
  val currentDate: LocalDate,
)
