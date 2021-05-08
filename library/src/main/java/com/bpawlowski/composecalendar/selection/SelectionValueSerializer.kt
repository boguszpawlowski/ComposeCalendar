package com.bpawlowski.composecalendar.selection

import com.bpawlowski.composecalendar.selection.SelectionValue.Multiple
import com.bpawlowski.composecalendar.selection.SelectionValue.None
import com.bpawlowski.composecalendar.selection.SelectionValue.Period
import com.bpawlowski.composecalendar.selection.SelectionValue.Single
import java.time.LocalDate

// TODO this would probably need refactoring
internal object SelectionValueSerializer {
  fun serialize(selectionValue: SelectionValue): List<String> = with(selectionValue) {
    when (this) {
      None -> emptyList()
      is Period -> listOfNotNull(start.toString(), end?.toString())
      is Multiple -> selection.map { it.toString() }
      is Single -> listOf(selection.toString())
    }
  }

  fun deserialize(rawData: Any?, selectionMode: SelectionMode): SelectionValue {
    val data = rawData as? List<String>? ?: return None
    val dates = data.map { LocalDate.parse(it) }
    return when (selectionMode) {
      SelectionMode.None -> None
      SelectionMode.Single -> deserializeSingle(dates)
      SelectionMode.Multiple -> deserializeMultiple(dates)
      SelectionMode.Period -> deserializePeriod(dates)
    }
  }

  private fun deserializeSingle(dates: List<LocalDate>): SelectionValue =
    dates.firstOrNull()?.let { Single(it) } ?: None

  private fun deserializePeriod(dates: List<LocalDate>): SelectionValue =
    dates.takeUnless { it.isEmpty() }?.let {
      Period(dates.first(), dates.getOrNull(1))
    } ?: None

  private fun deserializeMultiple(dates: List<LocalDate>): SelectionValue =
    dates.takeUnless { it.isEmpty() }
      ?.let { Multiple(it) }
      ?: None
}
