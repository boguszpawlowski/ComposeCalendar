package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Period
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

/**
 * In this sample, calendar composable is wired with an ViewModel. It's purpose is to show how to use
 * the composable in real world use-case, by an example implementation of a calendar
 * which can display planned recipes along with their prices
 */
@Composable
fun ViewModelSample() {
  val viewModel = remember { RecipeViewModel() }
  val recipes by viewModel.recipesFlow.collectAsState()
  val selectedPrice by viewModel.selectedRecipesPriceFlow.collectAsState(0)

  val state = rememberSelectableCalendarState(
    onSelectionChanged = viewModel::onSelectionChanged,
    initialSelectionMode = Period,
  )

  Column(
    Modifier.verticalScroll(rememberScrollState())
  ) {
    SelectableCalendar(
      calendarState = state,
      dayContent = { dayState ->
        RecipeDay(
          state = dayState,
          plannedRecipe = recipes.firstOrNull { it.date == dayState.date },
        )
      }
    )

    Spacer(modifier = Modifier.height(20.dp))
    Text(
      text = "Selected recipes price: $selectedPrice",
      style = MaterialTheme.typography.h6,
    )

    Spacer(modifier = Modifier.height(20.dp))
  }
}

/**
 * Custom implementation of DayContent, which shows a dot if there is an recipe planned for this day.
 */
@Composable
fun RecipeDay(
  state: DayState<DynamicSelectionState>,
  plannedRecipe: PlannedRecipe?,
  modifier: Modifier = Modifier,
) {
  val date = state.date
  val selectionState = state.selectionState

  val isSelected = selectionState.isDateSelected(date)

  Card(
    modifier = modifier
      .aspectRatio(1f)
      .padding(2.dp),
    elevation = if (state.isFromCurrentMonth) 4.dp else 0.dp,
    border = if (state.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colors.primary) else null,
    contentColor = if (isSelected) MaterialTheme.colors.secondary else contentColorFor(
      backgroundColor = MaterialTheme.colors.surface
    )
  ) {
    Column(
      modifier = Modifier.clickable {
        selectionState.onDateSelected(date)
      },
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = date.dayOfMonth.toString())
      if (plannedRecipe != null) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
        )
        Text(
          text = plannedRecipe.price.toString(),
          fontSize = 8.sp,
        )
      }
    }
  }
}

/**
 * Enables for changing current selection mode.
 */
@Composable
private fun SelectionControls(
  selectionState: DynamicSelectionState,
) {
  Text(
    text = "Calendar Selection Mode",
    style = MaterialTheme.typography.h6,
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
}

data class PlannedRecipe(
  val date: LocalDate,
  val price: Double,
)

/**
 * ViewModel exposing list of our recipes
 */
class RecipeViewModel : ViewModel() {

  private val selectionFlow = MutableStateFlow(emptyList<LocalDate>())
  val recipesFlow = MutableStateFlow(
    listOf(
      PlannedRecipe(LocalDate.now().plusDays(1), 20.0),
      PlannedRecipe(LocalDate.now().plusDays(3), 20.0),
      PlannedRecipe(LocalDate.now().plusDays(5), 10.0),
      PlannedRecipe(LocalDate.now().plusDays(-2), 25.0),
    )
  )
  val selectedRecipesPriceFlow = recipesFlow.combine(selectionFlow) { recipes, selection ->
    recipes.filter { it.date in selection }.sumOf { it.price }
  }

  fun onSelectionChanged(selection: List<LocalDate>) {
    selectionFlow.value = selection
  }
}

@Preview
@Composable
fun ViewModelSamplePreview() {
  MaterialTheme {
    ViewModelSample()
  }
}
