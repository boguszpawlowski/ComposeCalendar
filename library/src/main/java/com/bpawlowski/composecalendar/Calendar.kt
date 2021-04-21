package com.bpawlowski.composecalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bpawlowski.composecalendar.day.DayState
import java.time.LocalDate

@Suppress("UnusedPrivateMember")
@Composable
fun Calendar(
  modifier: Modifier = Modifier,
  initialDate: LocalDate = LocalDate.now(),
  dayContent: @Composable (DayState) -> Unit = {},
) {
  Box(modifier = modifier)
}
