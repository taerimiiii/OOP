package com.example.oop.ui.calender

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 상단 제목 박스 컴포넌트
@Composable
fun CalendarTitleCard(
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,
) {
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
            .background(
                color = whiteColor,
                shape = RoundedCornerShape(percent = 50)
            ),
            //.padding(horizontal = 16.dp, vertical = 1.dp),
        contentAlignment = Alignment.Center // 가운데 정렬
    ) {
        Text(
            text = text,
            color = blackColor,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
