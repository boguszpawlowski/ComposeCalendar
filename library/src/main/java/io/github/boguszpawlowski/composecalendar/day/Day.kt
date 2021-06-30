package io.github.boguszpawlowski.composecalendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
public interface Day {
  public val date: LocalDate
  public val isCurrentDay: Boolean
  public val isFromCurrentMonth: Boolean
}
