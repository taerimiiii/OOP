package com.example.oop.ui.calendar

import java.time.LocalDate
import java.time.YearMonth

/**
 * 캘린더 화면에서 표현과 로직 모두가 공유하는 상태 모델.
 * - [selectedDate]: 사용자가 택한 날짜, null이면 월간 뷰가 표시됨
 * - [visibleMonth]: 월간 캘린더가 현재 보여주는 달 (연/월)
 * - [today]: 오늘 날짜, 헤더나 강조 표시 등에 활용
 */
data class CalendarUiState(
    val selectedDate: LocalDate? = null,
    val visibleMonth: YearMonth = YearMonth.now(),
    val today: LocalDate = LocalDate.now(),
)
