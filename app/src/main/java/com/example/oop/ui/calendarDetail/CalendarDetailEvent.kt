package com.example.oop.ui.calendarDetail

// UI에서 발생하는 이벤트 정의
// interface로 선언 시, 핸들 이벤트 처리 할 때 else가 필수라 sealed 사용.
sealed class CalendarDetailEvent {
    data class OnMedicineTakenChanged(val itemSeq: String, val isTaken: Boolean) : CalendarDetailEvent()
}
