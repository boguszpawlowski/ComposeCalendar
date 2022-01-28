package io.github.boguszpawlowski.composecalendar.selection

import kotlinx.datetime.*

internal fun Collection<LocalDate>.startOrMax() = firstOrNull() ?: "+999999999-12-31".toLocalDate() //LocalDate.MAX

internal fun Collection<LocalDate>.endOrNull() = drop(1).lastOrNull()

internal fun Collection<LocalDate>.fillUpTo(date: LocalDate) =
  (0..date.toJavaLocalDate().toEpochDay() - first().toJavaLocalDate().toEpochDay()).map {
    first().plus(it, DateTimeUnit.DAY)
  }
