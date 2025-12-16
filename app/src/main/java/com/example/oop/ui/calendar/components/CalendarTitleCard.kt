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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// [수정] 우리가 Color.kt에 만든 이름으로 import 변경!
import com.example.oop.ui.theme.Black
import com.example.oop.ui.theme.PillGreen

@Composable
fun CalendarTitleCard(
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 120.dp,    // 기본값
) {

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(height)
            .border(
                // [수정] greenColor -> PillGreen
                border = BorderStroke(3.dp, PillGreen),
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(vertical = 5.dp),   // 위아래 요소와 5dp
        contentAlignment = Alignment.Center // 박스 내부 요소들 중앙 정렬
    ) {
        Text(
            text = text,
            // [수정] blackColor -> Black
            color = Black,
            fontSize = 16.sp
        )
    }
}