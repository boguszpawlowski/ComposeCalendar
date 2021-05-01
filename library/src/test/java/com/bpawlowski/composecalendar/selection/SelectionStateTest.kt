package com.bpawlowski.composecalendar.selection

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month.APRIL

internal class SelectionStateTest: ShouldSpec({

  val yesterday = LocalDate.of(2020, APRIL, 9)
  val today = LocalDate.of(2020, APRIL, 10)
  val tomorrow = LocalDate.of(2020, APRIL, 11)

  context("Selection state with SelectionMode.None") {
    should("not change selection after new value arrives") {
      val state = SelectionState(SelectionValue.None, SelectionMode.None)

      state.onDateSelected(LocalDate.now())

      state.selectionValue shouldBe SelectionValue.None
    }

    should("be able to change if mode has been changed") {
      val state = SelectionState(SelectionValue.None, SelectionMode.None)

      state.selectionMode = SelectionMode.Single
      state.onDateSelected(today)

      state.selectionValue shouldBe SelectionValue.Single(today)
    }
  }

  context("Selection state with SelectionMode.Single") {
    should("change state to single after day is selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Single)

      state.onDateSelected(today)

      state.selectionValue shouldBe SelectionValue.Single(today)
    }

    should("change state to none when same day is selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Single)

      state.onDateSelected(today)
      state.onDateSelected(today)

      state.selectionValue shouldBe SelectionValue.None
    }

    should("change to other day when selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Single)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      state.selectionValue shouldBe SelectionValue.Single(tomorrow)
    }

    should("not be mutable after selection mode is changed to None") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Single)

      state.selectionMode = SelectionMode.None
      state.onDateSelected(today)

      state.selectionValue shouldBe SelectionValue.None
    }
  }

  context("Selection state with SelectionMode.Multiple") {
    should("allow for multiple days selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Multiple)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      (state.selectionValue as SelectionValue.Multiple).selection shouldContainExactly listOf(today, tomorrow)
    }

    should("switch selection off once day is selected second time") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Multiple)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      (state.selectionValue as SelectionValue.Multiple).selection shouldContainExactly listOf(tomorrow)
    }
  }

  context("Selection state with SelectionMode.Period") {
    should("allow for period of days selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)

      (state.selectionValue as SelectionValue.Period).start shouldBe today
      (state.selectionValue as SelectionValue.Period).end shouldBe tomorrow
    }

    should("switch selection off once start day is selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      state.selectionValue shouldBe SelectionValue.None
    }
    should("change end date once the date selected is between start and the end") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Period)

      state.onDateSelected(yesterday)
      state.onDateSelected(tomorrow)
      state.onDateSelected(today)

      (state.selectionValue as SelectionValue.Period).start shouldBe yesterday
      (state.selectionValue as SelectionValue.Period).end shouldBe today
    }
    should("change start day once day before start is selected") {
      val state = SelectionState(SelectionValue.None, SelectionMode.Period)

      state.onDateSelected(today)
      state.onDateSelected(tomorrow)
      state.onDateSelected(yesterday)

      (state.selectionValue as SelectionValue.Period).start shouldBe yesterday
      (state.selectionValue as SelectionValue.Period).end shouldBe null
    }
  }
})
