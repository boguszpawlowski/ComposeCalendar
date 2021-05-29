# Compose Calendar

Compose Calendar is a composable handling all complexity of rendering calendar component and date selection.
Due to flexibility provided by slot API's, you can decide how the calendar will look like, the library will handle proper calendar elements arrangement and persistence of the state.

## Basic Usage
To show the basic version of the calendar, you can simply use the `Calendar` composable without passing any parameters:
```kotlin

  @Composable
  fun MainScreen() {
    Calendar()
  }

```
This chunk will render the calendar with default components for each day, and also month and week headers.
For the customization you should pass your own composable functions as day content, moth header etc.:
```kotlin

  @Composable
  fun MyDay(dayState: DayState) {
    Text(dayState.date.dayOfMonth.toString())
  }

  @Composable
  fun MainScreen() {
     Calendar(
        dayContent = { dayState -> MyDay(dayState) }
     )
  }

```
The same you can do for every customizable element:
- Day content - responsible for single day content
- Month header - responsible for showing the current month (and by default for changing the current month)
- Week header - responsible for showing the names of week days.
- Month container - wrapping the month content, it defaults to a plain `Box`, but can be any layout.

The `Calendar` composable accepts a `Modifier` for simple customization of the overall appearance.

## Calendar customization
Apart from rendering your own components inside the calendar, you can modify it by passing different properties.:
- `showAdjacentMonths` - whenever to render days from adjacent months. Defaults to `true`.
- `firstDayOfWeek` - you can pass the `DayOfWeek` which you want you week to start with. It defaults to the first day of week of the `Locale.default()`.

Apart from that, `Calendar` accepts a `Modifier` object like any other composable.

## State
Calendar composable holds its state as an `CalendarState` object, which consists of 2 properties.
- `MonthState` - current value of the presented month.
- `SelectionState` - current value and mode of the selection.

Both properties are represented by interfaces, so the default implementation can be overwritten if needed.
The calendar state is leveraging Compose saving mechanism, so that the state will survive any configuration change, or
process death.

### Initial state
Initial state is provided by the `rememberCalendarState()` function. If you need to change the initial conditions,
you can pass the params to it:

```kotlin

  @Composable
  fun MainScreen() {
    Calendar(
      calendarState = rememberCalendarState(
        initialDate = LocalDate.now().plusYears(1),
        initialSelection = SelectionValue.Single(LocalDate.now()),
        initialSelectionMode = SelectionMode.Single,
      )
    )
  }

```

### State hoisting
In case you need to react to the state changes, or change the state from the outside of the composable,
you need to hoist the state out of the `Calendar` composable:

```kotlin

  @Composable
  fun MainScreen() {
    val calendarState = rememberCalendarState()
    Calendar(calendarState = calendarState)
   
    // now you can manipulate the state from scope of this composable
    calendarState.monthState.currentMonth = MonthYear.of(2020, 5)
    // or extract Calendar's current state: 
    Text("Current selection of the calendar is: ${calendarState.selectionState.selection}")
  }

```

## Selection
The selection is always represented by a list of dates. The specific behavior of the selection is dependent on the currently active selection mode.
The library allows for 4 selection modes, represented by an enum class:
- `None` - no selection allowed - selection will always be an empty list.
- `Single` - only single day is selectable - selection will have 0/1 elements.
- `Multiple` - a list of dates can be selected.
- `Period` - selectable period - implemented by `start` and `end` dates. - selection will contain all dates between start and the end date.

The mode is a mutable property of the `SelectionState`, thus you can change the selection mode in the
runtime.

##### By default, changing selection mode will clear your selection. If you want different behavior, you have to create your `SelectionState` implementation and pass it into the `CalendarState`.
## License

    Copyright 2021 Bogusz Pawłowski

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
