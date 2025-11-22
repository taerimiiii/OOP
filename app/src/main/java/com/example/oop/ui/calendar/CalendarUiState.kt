package com.example.oop.ui.calendar

import java.time.LocalDate

// 화면 표현에 필요한 상태 묶음
data class CalendarUiState(
    val selectedDate: LocalDate? = null,
    val todayDate: LocalDate = LocalDate.now(),
    val monthlyAttendance: Int = 0,
    val lastMonthAttendance: Int = 0,
    val todayMedicineTaken: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
