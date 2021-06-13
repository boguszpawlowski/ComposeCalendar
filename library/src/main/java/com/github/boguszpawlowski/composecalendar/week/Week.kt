package com.github.boguszpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import com.github.boguszpawlowski.composecalendar.day.Day

@Immutable
internal data class Week(
  val isFirstWeekOfTheMonth: Boolean = false,
  val days: List<Day>,
)
