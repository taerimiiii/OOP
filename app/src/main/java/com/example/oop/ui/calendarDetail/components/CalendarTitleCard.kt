package com.example.oop.ui.calendarDetail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.oop.ui.theme.greenColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarTitleCard(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
) {

    val dateTitle = remember(selectedDate) {
        val datePart = selectedDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        "$datePart $dayOfWeek"
    }

    Column(
        modifier = modifier.padding(bottom = 13.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(40.dp)
                .border(
                    border = BorderStroke(3.dp, greenColor),
                    shape = RoundedCornerShape(percent = 50)
                )
                .padding(vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dateTitle,
                textAlign = TextAlign.Center
            )
        }
    }
}

