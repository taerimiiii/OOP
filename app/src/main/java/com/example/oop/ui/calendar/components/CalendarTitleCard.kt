package com.example.oop.ui.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarTitleCard(
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,    // 기본값
) {
    // 색 테마 상속 받아서 쓰기??
    val greenColor = Color(0xFF71E000)
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(height)
            .border(
                border = BorderStroke(3.dp, greenColor),
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(vertical = 5.dp),   // 위아래 요소와 5dp
        contentAlignment = Alignment.Center // 밗스 내부 요소들 중앙 정렬
    ) {
        Text(
            text = text,
            color = blackColor,
            fontSize = 16.sp
        )
    }
}

