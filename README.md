# Compose Calendar

Compose Calendar is a composable handling all complexity of rendering calendar component and date selection.
Due to flexibility provided by slot API's, you can decide how the calendar will look like, the library will handle proper calendar elements arrangement and it's state.

![Github Actions](https://github.com/boguszpawlowski/composecalendar/actions/workflows/check.yml/badge.svg?branch=main)
[<img src="https://img.shields.io/maven-central/v/io.github.boguszpawlowski.composecalendar/composecalendar.svg?label=release%20version"/>](https://search.maven.org/search?q=g:io.github.boguszpawlowski.composecalendar)
[<img src="https://img.shields.io/nexus/s/https/s01.oss.sonatype.org/io.github.boguszpawlowski.composecalendar/composecalendar.svg?label=snapshot%20version"/>](https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boguszpawlowski/composecalendar/)
## Setup
Library and it's snapshots are available on Maven Central repository.
```kotlin
  // module-level build.gradle
  dependecies {
    implementation "io.github.boguszpawlowski.composecalendar:composecalendar:<latest-version>"
  }
```

## Supported features
- Selection (single, multiple or a range of days)
- Every day as first day of week
- Showing/hiding adjacent months
- Month and week headers
- Customizable month container
- Fully customizable day content

## Basic Usage

### Static calendar
To show the basic version of the calendar, without any kind of selection mechanism, you can simply use the `StaticCalendar` composable without passing any parameters:
```kotlin

  @Composable
  fun MainScreen() {
    StaticCalendar()
  }

```
This chunk will render the calendar with default components for each day, and also month and week headers.
See the `StaticCalendarSample` file for a full example.

<img src="https://github.com/boguszpawlowski/ComposeCalendar/blob/main/blob/screenshot_1.jpg" width="260">

> :exclamation: By default, at first the calendar will show current month. If you want to start with some different date, you have to pass an `initialMonth` parameter to the initial state of the calendar. See [Initial State section](#initial-state)

### Selectable calendar
Calendar with a mechanism for selection. The default implementation uses `DynamicSelectionState` (see [Dynamic Selection section](#dynamic-selection-state)) which allows to change `SelectionMode` in the runtime.
```kotlin

  @Composable
  fun MainScreen() {
    SelectableCalendar()
  }

```
By the default, after changing the selection mode, selection is cleared.
See the `SelectableCalendarSample` file for a full example

> :exclamation: If you want to define your own selection behavior, please check out the [Custom Selection section](#custom-selection) and/or `CustomSelectionSample`.

### Calendar with custom components
For the customization you should pass your own composable functions as day content, moth header etc.:
```kotlin

  @Composable
  fun MyDay(dayState: DayState) {
    Text(dayState.date.dayOfMonth.toString())
  }

  @Composable
  fun MainScreen() {
     StaticCalendar(
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
See the `CustomComponentsSample` for a full example.

### Custom selection
As the selection state is represented by an interface, you can provide your own implementation, to suit your
use-case. E.g:
```kotlin
  class MonthSelectionState(
    initialSelection: YearMonth? = null,
  ) : SelectionState {
    private var selection by mutableStateOf(initialSelection)
  
    override fun isDateSelected(date: LocalDate): Boolean =
      date.yearMonth == selection
  
    override fun onDateSelected(date: LocalDate) {
      selection = if (date.yearMonth == selection) null else date.yearMonth
    }
  }
```
To use the defined selection state, you have to pass it into a generic version of `Calendar` composable.
This chunk is an implementation that will select all days in a clicked day's month. For a full example
please check out `CustomSelectionSample` file.

### Calendar properties customization
Apart from rendering your own components inside the calendar, you can modify it by passing different properties.:
- `showAdjacentMonths` - whenever to render days from adjacent months. Defaults to `true`.
- `firstDayOfWeek` - you can pass the `DayOfWeek` which you want you week to start with. It defaults to the first day of week of the `Locale.default()`.

Apart from this, `Calendar` you can pass a `Modifier` object like in any other composable.

## State
Calendar composable holds its state as an `CalendarState` object, which consists of 2 properties.
- `MonthState` - current value of the presented month.
- `SelectionState` - current value of the selection.

Both properties are represented by interfaces, so the default implementation can be overwritten if needed.
The calendar state is leveraging Compose saving mechanism, so that the state will survive any configuration change, or the process death.

### Initial state
Initial state for the static calendar is provided by the `rememberCalendarState()` function. If you need to change the initial conditions, you can pass the params to it:

```kotlin

  @Composable
  fun MainScreen() {
    StaticCalendar(
      calendarState = rememberCalendarState(
        initialDate = YearMonth.now().plusYears(1),
      )
    )
  }

```
In case of the selectable calendar, the state has additional parameters, used to calculate the initial selection:

```kotlin

  @Composable
  fun MainScreen() {
    SelectableCalendar(
      calendarState = rememberSelectableCalendarState(
        initialDate = YearMonth.now().plusYears(1),
        initialSelection = listOf(LocalDate.parse("20-01-2020")),
        initialSelectionMode = SelectionMode.Period,
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
    StaticCalendar(calendarState = calendarState)
   
    // now you can manipulate the state from scope of this composable
    calendarState.monthState.currentMonth = MonthYear.of(2020, 5)
  }

```

### Dynamic Selection State
By default, the `SelectableCalendar` is using a `DynamicSelectionState` implementation of `SelectionState`. The selection is kept as a list of `LocalDate` objects. For a purpose of flexibility, `DynamicSelectionState` allows for 4 different selection modes, each one varying how the selection is changing after interacting with the calendar. Furthermore, selection mode can be changed in the runtime, for some specific use-cases.
Selection modes are represented by `SelectionMode` enum, with following values:
- `None` - no selection allowed - selection will always be an empty list.
- `Single` - only single day is selectable - selection will contain one or zero days selected.
- `Multiple` - a list of dates can be selected.
- `Period` - selectable period - implemented by `start` and `end` dates. - selection will contain all dates between start and the end date.

## Compose Version
Library is currently build on top of Compose v1.0.0-rc2

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
