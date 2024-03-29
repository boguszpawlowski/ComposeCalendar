package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import io.github.boguszpawlowski.composecalendar.day.Day

@Immutable
internal data class WeekDays(
  val isFirstWeekOfTheMonth: Boolean = false,
  val days: List<Day>,
)
