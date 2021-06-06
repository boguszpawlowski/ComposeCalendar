package com.bpawlowski.composecalendar.selection

import java.time.LocalDate

public enum class SelectionMode {
  None,
  Single,
  Multiple,
  Period,
  ;
}

public sealed class Selection {
  public data class Single(val selection: LocalDate? = null)
}
