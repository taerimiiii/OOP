package com.example.oop.ui.calender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.PillTopBar

// UI 화면을 구성하는 Composable 함수
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    // 실제 캘린더 UI
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "캘린더 화면을 구성하세요.",
            modifier = Modifier.padding(24.dp)
        )
    }
}