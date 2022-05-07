package io.github.boguszpawlowski.composecalendar.sample

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import org.junit.Rule
import org.junit.Test
import java.time.YearMonth

internal class ScrollBehaviorTest {

  private val initialMonth = YearMonth.of(2012, 5)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun monthChangeTest() {
    composeTestRule.setContent {
      StaticCalendar(
        calendarState = rememberCalendarState(initialMonth = initialMonth)
      )
    }

    composeTestRule.onNodeWithTag("Decrement").performClick()
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("April")
    composeTestRule.onNodeWithTag("Increment").performClick()
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("May")
  }

  @Test
  fun scrollMonthChangeTest() {
    composeTestRule.setContent {
      StaticCalendar(
        calendarState = rememberCalendarState(initialMonth = initialMonth)
      )
    }

    composeTestRule.onNodeWithTag("MonthPager").performTouchInput { swipeLeft() }
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("June")
    composeTestRule.onNodeWithTag("MonthPager").performTouchInput { swipeRight() }
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("May")
  }
}
