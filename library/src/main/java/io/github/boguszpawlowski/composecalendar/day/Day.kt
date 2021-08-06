package io.github.boguszpawlowski.composecalendar.day

import java.time.LocalDate

/**
 * Container for basic info about the displayed day
 *
 * @param date local date of the day
 * @param isCurrentDay whenever the day is the today's date
 * @param isFromCurrentMonth whenever the day is from currently rendered month
 */
public interface Day {
  public val date: LocalDate
  public val isCurrentDay: Boolean
  public val isFromCurrentMonth: Boolean
}
