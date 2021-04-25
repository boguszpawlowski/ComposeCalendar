package com.bpawlowski.composecalendar.day

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionState
import com.bpawlowski.composecalendar.selection.SelectionStateImpl
import com.bpawlowski.composecalendar.selection.SelectionValue
import com.bpawlowski.composecalendar.selection.SelectionValue.Multiple
import com.bpawlowski.composecalendar.selection.SelectionValue.None
import java.time.LocalDate

@Composable
public fun DayContent(
  day: Day,
  selectionState: SelectionState,
  modifier: Modifier = Modifier,
) {
  val date = day.date

  val isSelected = when (val selection = selectionState.selectionValue) {
    None -> false
    is Multiple -> selection.selection.contains(date)
    is SelectionValue.Single -> selection.date == date
    is SelectionValue.Period -> selection.contains(date)
  }

  Card(
    modifier = modifier.padding(2.dp),
    elevation = if (day.isFromCurrentMonth) 4.dp else 0.dp,
    border = if (day.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colors.primary) else null,
    contentColor = if (isSelected) MaterialTheme.colors.secondary else contentColorFor(
      backgroundColor = MaterialTheme.colors.background
    )
  ) {
    Row(
      modifier = Modifier.clickable { selectionState.onDateSelected(date) },
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = date.dayOfMonth.toString())
    }
  }
}

@Preview
@Composable
private fun DayPreview() {
  val date = LocalDate.now()
  val state = Day(date)
  DayContent(state, selectionState = SelectionStateImpl(SelectionValue.None, SelectionMode.None))
}
