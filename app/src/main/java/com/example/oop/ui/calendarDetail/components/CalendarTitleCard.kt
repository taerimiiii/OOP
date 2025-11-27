package com.example.oop.ui.calendarDetail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarTitleCard(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    val greenColor = Color(0xFF71E000)
    val blackColor = Color(0xFF000000)

    val headerText = remember(selectedDate) {
        val datePart = selectedDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        "$datePart $dayOfWeek\n     일일 복용 약 확인"
    }

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(80.dp)
            .border(
                border = BorderStroke(3.dp, greenColor),
                shape = RoundedCornerShape(percent = 50)
            )
            .padding(vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = headerText,
            color = blackColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

