package com.example.oop.ui.calendar

import java.time.LocalDate
import java.time.YearMonth

/**
 * 캘린더 화면 전체에서 발생하는 사용자 입력/상태 변화를
 * 뷰모델에게 전달하기 위한 이벤트 모음.
 */
sealed interface CalendarEvent {
    data class DateSelected(val date: LocalDate) : CalendarEvent
    data class VisibleMonthChanged(val month: YearMonth) : CalendarEvent
    data object ResetSelection : CalendarEvent
}