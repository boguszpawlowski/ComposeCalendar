package com.bpawlowski.composecalendar

import androidx.compose.runtime.Immutable
import java.time.YearMonth

@Immutable
data class Header(
  val month: YearMonth,
)
