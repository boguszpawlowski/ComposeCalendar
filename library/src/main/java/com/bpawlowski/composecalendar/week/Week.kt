package com.bpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import com.bpawlowski.composecalendar.day.Day

@Immutable
internal data class Week(
  val isFirstWeekOfTheMonth: Boolean = false,
  val days: List<Day>,
)
