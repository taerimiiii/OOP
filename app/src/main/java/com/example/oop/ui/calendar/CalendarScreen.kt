package com.example.oop.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.calendarDetail.CalendarDetailScreen
import java.time.LocalDate

// UI 화면을 구성하는 Composable 함수
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    if (selectedDate == null) {
        Column(
            modifier = modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarTitleCard(text = "약먹자 님의 섭취 기록", height = 40.dp) // 사용자 이름으로 변경 필요
            MonthCalendar(
                onDateSelected = { date -> selectedDate = date }
            )
        }
    } else {
        CalendarDetailScreen(
            modifier = modifier,
            selectedDate = selectedDate!!,
            onBack = { selectedDate = null }
        )
    }
}