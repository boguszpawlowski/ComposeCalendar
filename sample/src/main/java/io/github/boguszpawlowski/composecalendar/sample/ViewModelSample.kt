package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode.Period
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import kotlin.random.Random
import kotlin.random.nextLong

/**
 * In this sample, calendar composable is wired with an ViewModel. It's purpose is to show how to use
 * the composable in real world use-case, by an example implementation of a calendar
 * which can display planned recipes along with their prices. The recipes will also be re-fetched from
 * fake API every time month changes.
 */
@Composable
fun ViewModelSample() {
  val viewModel = remember { RecipeViewModel() }
  val recipes by viewModel.recipesFlow.collectAsState()
  val selectedPrice by viewModel.selectedRecipesPriceFlow.collectAsState(0)
  val monthState = rememberSaveable(saver = MonthState.Saver()) {
    MonthState(initialMonth = YearMonth.now())
  }

  LaunchedEffect(monthState) {
    snapshotFlow { monthState.currentMonth }
      .onEach { viewModel.onMonthChanged(it) }
      .launchIn(this)
  }

  val state = rememberSelectableCalendarState(
    confirmSelectionChange = { viewModel.onSelectionChanged(it); true },
    monthState = monthState,
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
      },
      onMonthSwipe = { Timber.tag("new month").d(it.toString()) },
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

data class PlannedRecipe(
  val date: LocalDate,
  val price: Double,
)

/**
 * ViewModel exposing list of our recipes
 */
class RecipeViewModel : ViewModel() {

  private val selectionFlow = MutableStateFlow(emptyList<LocalDate>())
  val recipesFlow = MutableStateFlow(getRecipes())
  val selectedRecipesPriceFlow = recipesFlow.combine(selectionFlow) { recipes, selection ->
    recipes.filter { it.date in selection }.sumOf { it.price }
  }

  fun onSelectionChanged(selection: List<LocalDate>) {
    selectionFlow.value = selection
  }

  fun onMonthChanged(newMonth: YearMonth) = viewModelScope.launch {
    recipesFlow.value = getRecipesFromApi(startingDay = newMonth.atDay(15))
  }

  private fun getRecipes(startingDay: LocalDate = LocalDate.now()) = listOf(
    PlannedRecipe(startingDay.plusDays(Random.nextLong(1L..3L)), 20.0),
    PlannedRecipe(startingDay.plusDays(Random.nextLong(4L..7L)), 20.0),
    PlannedRecipe(startingDay.plusDays(Random.nextLong(8L..11L)), 10.0),
    PlannedRecipe(startingDay.plusDays(Random.nextLong(-5L..-1L)), 25.0),
  )

  /**
   * Simulated API call for new recipes
   */
  private suspend fun getRecipesFromApi(startingDay: LocalDate) = withContext(Dispatchers.Default) {
    delay(100)
    getRecipes(startingDay)
  }
}

@Preview
@Composable
fun ViewModelSamplePreview() {
  MaterialTheme {
    ViewModelSample()
  }
}
