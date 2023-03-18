package io.github.boguszpawlowski.composecalendar.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

context(LazyListState) internal fun <T> Flow<T>.throttleOnOffset() =
  combine(
    snapshotFlow { firstVisibleItemScrollOffset }
  ) { newMonth, offset ->
    newMonth to (offset <= MinimalOffsetForEmit)
  }.filter { (_, shouldUpdate) ->
    shouldUpdate
  }.map { (newValue, _) -> newValue }

private const val MinimalOffsetForEmit = 10
