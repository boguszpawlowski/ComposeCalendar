package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.SelectableWeekCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableWeekCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode

@Composable
fun WeekCalendarSample() {
  val calendarState = rememberSelectableWeekCalendarState()

  Column(
    Modifier.verticalScroll(rememberScrollState())
  ) {
    SelectableWeekCalendar(calendarState = calendarState)

    SelectionControls(selectionState = calendarState.selectionState)
  }
}

@Composable
private fun SelectionControls(
  selectionState: DynamicSelectionState,
) {
  Text(
    text = "Calendar Selection Mode",
    style = MaterialTheme.typography.h5,
  )
  SelectionMode.values().forEach { selectionMode ->
    Row(modifier = Modifier.fillMaxWidth()) {
      RadioButton(
        selected = selectionState.selectionMode == selectionMode,
        onClick = { selectionState.selectionMode = selectionMode }
      )
      Text(text = selectionMode.name)
      Spacer(modifier = Modifier.height(4.dp))
    }
  }

  Text(
    text = "Selection: ${selectionState.selection.joinToString { it.toString() }}",
    style = MaterialTheme.typography.h6,
  )
}
