package io.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
internal class WeekDay(
  override val date: LocalDate,
  override val isCurrentDay: Boolean,
  override val isFromCurrentMonth: Boolean
) : Day
