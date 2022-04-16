package io.github.boguszpawlowski.composecalendar.kotlinxDateTime

public fun YearMonth.toJavaYearMonth(): java.time.YearMonth = java.time.YearMonth.of(year, month)

public fun java.time.YearMonth.toKotlinYearMonth(): YearMonth = YearMonth.of(year, month)
