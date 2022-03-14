package io.github.boguszpawlowski.composecalendar.pager

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.ints.shouldBeExactly

internal class PagerHelperTest : ShouldSpec({

  context("Recalculation of page index") {
    val startIndex = StartIndex
    val pageCount = 3

    should("return correct numbers for indices") {
      listOf(
        startIndex to 1,
        startIndex + 1 to 2,
        startIndex + 2 to 0,
        startIndex + 3 to 1,
        startIndex + 4 to 2,
        startIndex + 5 to 0,
        startIndex - 1 to 0,
        startIndex - 2 to 2,
        startIndex - 3 to 1,
        startIndex - 4 to 0,
        startIndex - 5 to 2,
      ).forAll { (index, expectedActualPage) ->
        index.toIndex(
          startIndex = startIndex,
          pageCount = pageCount,
        ) shouldBeExactly expectedActualPage
      }
    }
  }
})
