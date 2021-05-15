# Compose Calendar

Compose Calendar is a composable handling all complexity of rendering calendar and calendar selection.
Due to Jetpack Compose declarative style, you can decide how the calendar will look like, the library
will handle proper elements arrangement and persistence of the state.

## Basic Usage
To show the basic version of the calendar, you can simply use the `Calendar` composable:
```kotlin

  @Composable
  fun MainScreen() {
    Calendar()
  }

```
It will render the calendar with the current month showing, and default components for each day as well as month and week 
headers.
For the customization you should pass your own composable functions as day content etc.:
```kotlin

  @Composable
  fun MyDay(dayState: DayState) {
    Text(dayState.date.dayOfMonth.toString())
  }

  @Composable
  fun MainScreen() {
     Calendar(
        dayContent = { MyDay(it) }
     )
  }

```
The same you can do for every customizable element:
 - Day content - responsible for single day content
 - Month header - responsible for showing the current month (and by default for changing the current month)
 - Week header - responsible for showing the names of week days.
 - Month container - wrapping the month content, it defaults to a plain `Box`, but can be any layout.

The `Calendar` composable accepts a `Modifier` for simple customization of the overall appearance. 

## State
Calendar composable holds its state as an object of `CalendarState` object, which consists of 2 properties.
- `MonthState` - holding current value of the presented month.
- `SelectionState` - holding current value and mode of the selection. 
  
Both properties are represented by interfaces, so the default implementation can be overwritten if needed.
The calendar state is leveraging saving mechanism, so that the state will survive any configuration change, or
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
    Text("Current selection of the calendar is: ${calendarState.selectionState.selectionValue}")
  }

```

## Selection
The library allows for 4 selection modes:
  - `None` - no selection allowed
  - `Single` - only single day is selectable
  - `Multiple` - a list of dates can be selected
  - `Period` - selectable period - implemented by `start` and `end` dates.

Each of this modes maps to the `SelectionValue` with the same name. The default implementation of the
state guarantees, that the value will be of the type allowed by the mode.

## Additional features (TODO)
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
