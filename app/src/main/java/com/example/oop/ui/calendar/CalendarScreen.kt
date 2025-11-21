package com.example.oop.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendarDetail.CalendarDetailScreen

/**
 * 월간 ↔ 주간 상세를 전환하는 상위 캘린더 화면.
 * 상태는 [CalendarViewModel] 이 관리하고 UI는 상태에 따라 렌더링만 수행한다.
 */
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    calendarViewModel: CalendarViewModel = viewModel(),
) {
    val uiState by calendarViewModel.uiState.collectAsState()
    val selectedDate = uiState.selectedDate

    if (selectedDate == null) {
        Column(
            modifier = modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarTitleCard(text = "약먹자 님의 섭취 기록", height = 40.dp) // 사용자 이름으로 변경 필요
            MonthCalendar(
                selectedDate = selectedDate,
                visibleMonth = uiState.visibleMonth,
                onDateSelected = { date -> calendarViewModel.onEvent(CalendarEvent.DateSelected(date)) },
                onVisibleMonthChange = { month -> calendarViewModel.onEvent(CalendarEvent.VisibleMonthChanged(month)) },
            )
        }
    } else {
        CalendarDetailScreen(
            modifier = modifier,
            selectedDate = selectedDate,
            onBack = { calendarViewModel.onEvent(CalendarEvent.ResetSelection) },
            onDateSelected = { date -> calendarViewModel.onEvent(CalendarEvent.DateSelected(date)) },
        )
    }
}