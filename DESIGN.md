# General design assumptions / thoughts

## Requirements

The main purpose is to provide flexible Calendar Composable function. It should enable:

- Rendering custom view of particular day (Utilizing Slot approach)
  The library will provide standard composables for day views, but user will be able to override
  them
- Customizable selection mode (None, Day, Period)
  This probably will work as simple enum passed to the `Calendar` composable. TBD - either library
  should expose 2 different versions of the calendar (selectable, non-selectable) or one,
  customizable. The issue with one single function: the composable for rendering days will have to
  accept `isSelected`
  parameter, which shouldn't be present on the non-selectable version. Other solution is to create
  Non-selectable version which will utilize selectable and filter out unnecessary parameters itself.
- Customizable view mode (Week, Month)

  Whole customization for selection and view mode should ideally be provided via custom `Modifier` extensions within
  the `CalendarScope`.

- Customizable animations - WIP
