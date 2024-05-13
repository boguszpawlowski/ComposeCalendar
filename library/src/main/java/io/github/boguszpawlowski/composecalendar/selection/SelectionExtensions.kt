package io.github.boguszpawlowski.composecalendar.selection

import android.annotation.SuppressLint
import java.time.LocalDate

@SuppressLint("NewApi")
internal fun Collection<LocalDate>.startOrMax() = firstOrNull() ?: LocalDate.MAX

internal fun Collection<LocalDate>.endOrNull() = drop(1).lastOrNull()

@SuppressLint("NewApi")
internal fun Collection<LocalDate>.fillUpTo(date: LocalDate) =
  (0..date.toEpochDay() - first().toEpochDay()).map {
    first().plusDays(it)
  }
