@file:Suppress("UnderscoresInNumericLiterals")

package io.github.boguszpawlowski.composecalendar.selection

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month.APRIL

internal class SelectionStateTest : ShouldSpec({

  val yesterday = LocalDate.of(2020, APRIL, 9)
  val today = LocalDate.of(2020, APRIL, 10)
  val tomorrow = LocalDate.of(2020, APRIL, 11)

  context("Selection state with SelectionMode.None") {
    should("not change selection after new value arrives") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.None)

      state.onDateSelected(LocalDate.now())

      state.selection shouldBe emptyList()
    }

    should("be able to change if mode has been changed") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.None)

      state.selectionMode = SelectionMode.Single
      state.onDateSelected(today)

      state.selection shouldBe listOf(today)
    }
  }

  context("Selection state with SelectionMode.Single") {
    should("change state to single after day is selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Single)

      state.onDateSelected(today)

      state.selection shouldBe listOf(today)
    }

    should("change state to none when same day is selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Single)

      state.onDateSelected(today)
      state.onDateSelected(today)

      state.selection shouldBe emptyList()
    }

    should("change to other day when selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Single)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      state.selection shouldBe listOf(tomorrow)
    }

    should("not be mutable after selection mode is changed to None") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Single)

      state.selectionMode = SelectionMode.None
      state.onDateSelected(today)

      state.selection shouldBe emptyList()
    }
  }

  context("Selection state with SelectionMode.Multiple") {
    should("allow for multiple days selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Multiple)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      state.selection shouldContainExactly listOf(
        today,
        tomorrow
      )
    }

    should("switch selection off once day is selected second time") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Multiple)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      state.selection shouldContainExactly listOf(tomorrow)
    }
  }

  context("Selection state with SelectionMode.Period") {
    should("allow for period of days selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      state.selection.first() shouldBe today
      state.selection.last() shouldBe tomorrow
    }

    should("switch selection off once start day is selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      state.selection shouldBe emptyList()
    }
    should("change end date once the date selected is between start and the end") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Period)

      state.onDateSelected(yesterday)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      state.selection.first() shouldBe yesterday
      state.selection.last() shouldBe today
    }
    should("change start day once day before start is selected") {
      val state = DynamicSelectionState(emptyList(), SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(yesterday)

      state.selection.first() shouldBe yesterday
      state.selection.endOrNull() shouldBe null
    }
  }
})
