package io.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

// TODO some better naming
@Immutable
public class WeekDay(
  override val date: LocalDate,
  override val isCurrentDay: Boolean,
  override val isFromCurrentMonth: Boolean
) : Day
