package com.example.oop.ui.calendar

import java.time.LocalDate

// UI에서 발생하는 이벤트 정의
// interface로 선언 시, 핸들 이벤트 처리 할 때 else가 필수라 sealed 사용.
sealed class CalendarEvent {
    data class OnDateSelected(val date: LocalDate) : CalendarEvent()
    object OnMoveDetailButton : CalendarEvent()
    object OnPreviousMonth : CalendarEvent()
    object OnNextMonth : CalendarEvent()
}

// object : 데이터가 필요 없다. 즉, class 보다 메모리를 효율적으로 쓸 수 있다.
// 날짜 선택은 날짜 데이터가 필요해서 클래스로 사용.