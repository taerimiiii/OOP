package com.example.oop.ui.calendar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendar.CalendarUtils
import java.time.LocalDate
import com.example.oop.ui.theme.blackColor
import com.example.oop.ui.theme.backGrayColor
import com.example.oop.ui.theme.greenColor
import com.example.oop.ui.theme.whiteColor

@Composable
fun MoveDetailPageButton(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate?,       // 년월일, 맨 처음 캘린더 페이지 접속 시 null
    onClick: () -> Unit,            // 매개변수X, 반환값X
) {

    Button(
        onClick = onClick,
        enabled = selectedDate != null, // 비/활성화 여부
        modifier = modifier
            .width(170.dp)
            .height(200.dp)
            .padding(vertical = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = greenColor,
            contentColor = whiteColor,
            disabledContainerColor = backGrayColor,
            disabledContentColor = blackColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // 가로 중앙
            verticalArrangement = Arrangement.Center            // 세로 중앙
        ) {
            if (selectedDate != null) {
                Text(
                    text = CalendarUtils.formatOutMonthDate(selectedDate),
                    fontSize = 18.sp,
                    color = blackColor,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
            Text(
                text = "복용 체크하기",
                fontSize = 18.sp,
                color = blackColor
            )
        }
    }
}

