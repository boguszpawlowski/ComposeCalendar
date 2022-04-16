package io.github.boguszpawlowski.composecalendar.kotlinxDateTime

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.temporal.WeekFields
import java.util.Locale

public fun LocalDate.Companion.now(): LocalDate =
  Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

public val Locale.firstDayOfWeek: DayOfWeek
  get() = WeekFields.of(this).firstDayOfWeek
