package com.example.oop.ui.calender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.daysOfWeek

// UI 화면을 구성하는 Composable 함수
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    val daysOfWeek = daysOfWeek()

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarTitleCard(text = "약먹자 님의 섭취 기록", height = 40.dp)
        MonthCalendar()
    }



}