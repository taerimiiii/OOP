package com.example.oop.ui.calendar

import java.time.LocalDate
import java.time.YearMonth

// 화면 표현에 필요한 상태 묶음
// 상태는 ViewModel 에서 관리
// data class : copy() 자동 생성 -> ViewModel에서 상태를 불변으로 업데이트할 때 좋음.
data class CalendarUiState(
    val selectedDate: LocalDate? = null,
    val todayDate: LocalDate = LocalDate.now(),
    val currentSeeMonth: YearMonth = YearMonth.now(), // 현재 보이는 월
    val monthCount: Int = 0,
    val lastMonthCount: Int = 0,
    val todayMedicineTaken: Boolean = false,
)
