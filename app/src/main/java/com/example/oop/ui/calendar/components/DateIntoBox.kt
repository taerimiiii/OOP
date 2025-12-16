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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendar.CalendarUtils
// [수정] 우리 프로젝트의 진짜 색깔 이름들로 변경!
import com.example.oop.ui.theme.Black
import com.example.oop.ui.theme.PillGreen
import com.example.oop.ui.theme.White
import java.time.LocalDate

@Composable
fun DateIntoBox(
    title: String,
    date: LocalDate?,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .width(185.dp)
            .height(80.dp)
            .border(
                // [수정] lightGreenColor -> PillGreen
                border = BorderStroke(3.dp, PillGreen),
                shape = RoundedCornerShape(percent = 30)
            )
            .background(
                // [수정] whiteColor -> White
                color = White,
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
                // [수정] blackColor -> Black
                color = Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (date != null) {
                    CalendarUtils.formatOutYearMonthDate(date)
                } else {
                    "없음"
                },
                fontSize = 18.sp,
                // [수정] blackColor -> Black
                color = Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
    }
}