package com.example.oop.ui.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendar.CalendarUtils
import java.time.LocalDate

@Composable
fun DateIntoBox(
    title: String,
    date: LocalDate?,
    modifier: Modifier = Modifier
) {
    // 나중에 색 테마 만들어서 쓰기
    val lightGreenColor = Color(0xFFD6F4B6)
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)
    
    Box(
        modifier = modifier
            .width(185.dp)
            .height(80.dp)
            .border(
                border = BorderStroke(3.dp, lightGreenColor),
                shape = RoundedCornerShape(percent = 30)
            )
            .background(
                color = whiteColor,
                shape = RoundedCornerShape(percent = 30)
            ),
        contentAlignment = Alignment.Center // 중앙 정렬
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙
            verticalArrangement = Arrangement.Center            // 세로 중앙
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = blackColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (date != null) {
                            CalendarUtils.formatOutYearMonthDate(date)
                        } else {
                            "없음"
                        },
                fontSize = 18.sp,
                color = blackColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
    }
}

