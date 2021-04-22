package com.bpawlowski.composecalendar.week

import androidx.compose.runtime.Immutable
import com.bpawlowski.composecalendar.day.Day
import java.time.LocalDate

@Immutable
data class Week(
  val isFirstWeekOfTheMonth: Boolean = false,
  val days: List<Day>,
)
