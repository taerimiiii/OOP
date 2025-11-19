package com.example.oop.ui.calender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalenderDetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        CalendarTitleCard(text = "일일 복용 약 확인", height = 40.dp)
    }
}