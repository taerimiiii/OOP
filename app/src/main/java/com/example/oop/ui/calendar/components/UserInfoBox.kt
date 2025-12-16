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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// [수정] 우리 프로젝트의 진짜 색깔 이름들로 변경!
import com.example.oop.ui.theme.Black
import com.example.oop.ui.theme.PillGreen
import com.example.oop.ui.theme.White

@Composable
fun UserInfoBox(
    monthCount: Int,
    lastMonthCount: Int,
    todayMedicineTaken: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(185.dp)
            .height(350.dp)
            .border(
                // [수정] lightGreenColor -> PillGreen (테두리는 진한 녹색)
                border = BorderStroke(3.dp, PillGreen),
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 0,
                    bottomStartPercent = 30,
                    bottomEndPercent = 30
                )
            )
            .background(
                // [수정] whiteColor -> White
                color = White,
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 0,
                    bottomStartPercent = 30,
                    bottomEndPercent = 30
                )
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "월간 출석 : ${monthCount}회",
                fontSize = 18.sp,
                // [수정] blackColor -> Black
                color = Black
            )
            Text(
                text = "지난달 출석 : ${lastMonthCount}회",
                fontSize = 18.sp,
                // [수정] blackColor -> Black
                color = Black
            )
            Text(
                text = "오늘의 약 복용 : ${if (todayMedicineTaken) "O" else "X"}",
                fontSize = 18.sp,
                // [수정] blackColor -> Black
                color = Black
            )
        }
    }
}