package com.example.oop.ui.calendar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendar.CalendarUtils
import java.time.LocalDate

@Composable
fun CheckButton(
    selectedDate: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val blackColor = Color(0xFF000000)
    val greenColor = Color(0xFF71E000)
    val darkGrayColor = Color(0xFFD9D9D9)
    val whiteColor = Color(0xFFFFFFFF)

    Button(
        onClick = onClick,
        enabled = selectedDate != null,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = greenColor,
            contentColor = whiteColor,
            disabledContainerColor = darkGrayColor,
            disabledContentColor = blackColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedDate != null) {
                Text(
                    text = CalendarUtils.formatShortDate(selectedDate),
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

