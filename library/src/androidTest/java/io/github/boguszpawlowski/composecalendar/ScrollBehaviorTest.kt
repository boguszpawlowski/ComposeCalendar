package io.github.boguszpawlowski.composecalendar

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth

internal class ScrollBehaviorTest {

  private val initialMonth = YearMonth.of(2012, 5)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun MonthChangeTest() {
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
  fun ScrollMonthChangeTest() {
    composeTestRule.setContent {
      StaticCalendar(
        calendarState = rememberCalendarState(initialMonth = initialMonth)
      )
    }

    composeTestRule.onNodeWithTag("MonthPager").performGesture { swipeLeft() }
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("June")
    composeTestRule.onNodeWithTag("MonthPager").performGesture { swipeRight() }
    composeTestRule.onNodeWithTag("MonthLabel", true).assertTextEquals("May")
  }
}
