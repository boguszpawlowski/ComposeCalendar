package io.github.boguszpawlowski.composecalendar.selection

/**
 * Selection modes for the [DynamicSelectionState]
 * None - no selection allowed
 * Single - only one date selected
 * Multiple - multiple dates can be selected
 * Period - period can be selected
 */
public enum class SelectionMode {
  None,
  Single,
  Multiple,
  Period,
  ;
}
