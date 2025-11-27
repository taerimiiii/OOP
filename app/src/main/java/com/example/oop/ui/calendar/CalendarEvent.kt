package com.example.oop.ui.calendar

import java.time.LocalDate

// UI에서 발생하는 이벤트 정의
sealed class CalendarEvent {
    data class OnDateSelected(val date: LocalDate) : CalendarEvent()
    object OnMoveDetailButton : CalendarEvent()
    object OnPreviousMonth : CalendarEvent()
    object OnNextMonth : CalendarEvent()
}
