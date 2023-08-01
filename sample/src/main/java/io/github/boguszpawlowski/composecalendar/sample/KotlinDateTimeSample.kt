package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.kotlinxDateTime.now
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Multiple
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import timber.log.Timber

@Composable
fun KotlinXDateTimeSample() {
  var selection by remember { mutableStateOf(emptyList<LocalDate>()) }

  Column {
    DateTimeCalendar(
      today = LocalDate.now(),
      onSelectionChanged = { selection = it },
      dayContent = { DayContent(dayState = it) },
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = "Selection: ${selection.joinToString { it.toString() }}",
      style = MaterialTheme.typography.h6,
    )
  }
}

@Composable
fun BoxScope.DayContent(
  dayState: KotlinDayState<DynamicSelectionState>,
) {
  val isSelected = dayState.selectionState.isDateSelected(dayState.date)

  Text(
    text = dayState.date.dayOfMonth.toString(),
    modifier = Modifier
      .fillMaxWidth()
      .align(Alignment.Center)
      .aspectRatio(1f)
      .clickable {
        dayState.selectionState.onDateSelected(dayState.date)
      },
    color = if (isSelected) Color.Red else Color.Unspecified,
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.h6,
  )
}

@Composable
fun DateTimeCalendar(
  today: LocalDate,
  onSelectionChanged: (List<LocalDate>) -> Unit,
  dayContent: @Composable BoxScope.(KotlinDayState<DynamicSelectionState>) -> Unit,
) {
  SelectableCalendar(
    calendarState = rememberSelectableCalendarState(
      confirmSelectionChange = { selection -> onSelectionChanged(selection.map { it.toKotlinLocalDate() }); true },
      initialSelectionMode = Multiple,
    ),
    today = today.toJavaLocalDate(),
    showAdjacentMonths = false,
    dayContent = { dayState ->
      dayContent(
        KotlinDayState(
          date = dayState.date.toKotlinLocalDate(),
          isCurrentDay = dayState.isCurrentDay,
          selectionState = dayState.selectionState,
        )
      )
    },
    onMonthSwipe = { Timber.tag("new month").d(it.toString()) },
  )
}

data class KotlinDayState<T : SelectionState>(
  val date: LocalDate,
  val isCurrentDay: Boolean,
  val selectionState: T,
)

private fun SelectionState.isDateSelected(date: LocalDate) = isDateSelected(date.toJavaLocalDate())
private fun SelectionState.onDateSelected(date: LocalDate) = onDateSelected(date.toJavaLocalDate())
