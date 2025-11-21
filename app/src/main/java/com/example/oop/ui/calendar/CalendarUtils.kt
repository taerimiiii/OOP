package com.example.oop.ui.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * 날짜 계산/서식 helper는 object로 관리하기.
 * UI 단에서 공통 포맷/표시 규칙을 공유하도록 중앙 집중화한다.
 */
object CalendarUtils {
    private val dayFormatter = DateTimeFormatter.ofPattern("dd", Locale.getDefault())

    fun formatDayNumber(date: LocalDate): String = dayFormatter.format(date)
}

/**
 * 연-월 값을 로캘 기반 문자열로 변환.
 */
fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}

fun DayOfWeek.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}