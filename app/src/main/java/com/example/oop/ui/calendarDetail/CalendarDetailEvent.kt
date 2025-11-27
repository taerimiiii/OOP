package com.example.oop.ui.calendarDetail

// UI에서 발생하는 이벤트 정의
sealed class CalendarDetailEvent {
    data class OnMedicineTakenChanged(val itemSeq: String, val isTaken: Boolean) : CalendarDetailEvent()
}
