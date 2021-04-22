package com.bpawlowski.composecalendar.header

import androidx.compose.runtime.Immutable
import java.time.YearMonth

@Immutable
public data class Header(
  val month: YearMonth,
)
