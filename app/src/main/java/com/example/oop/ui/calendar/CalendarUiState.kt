package com.example.oop.ui.calendar

import java.time.LocalDate

// 화면 표현에 필요한 상태 묶음
data class CalendarUiState(
    // 임시 코드
    val selectedDate: LocalDate = LocalDate.now()
)
