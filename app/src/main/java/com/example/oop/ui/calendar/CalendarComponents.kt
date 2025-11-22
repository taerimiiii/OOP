package com.example.oop.ui.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.rememberCoroutineScope
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.example.oop.ui.calendar.CalendarUtils
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.launch

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
            .padding(vertical = 5.dp)
            .background(
                color = whiteColor,
                shape = RoundedCornerShape(percent = 50)
            ),
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

// 요일 하나 박스
@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape) // 클릭하면 원형 배경
            .background(color = if (isSelected) Color.Green else Color.Transparent)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.Gray
        )
    }
}

// 월화수목금토일
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = Color.Gray
            )
        }
    }
}

// 월간 캘린더
@Composable
fun MonthCalendar(
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
) {
    val currentMonth = remember { CalendarUtils.getCurrentMonth() }
    val startMonth = remember { currentMonth.minusMonths(10) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(10) } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek() }
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val visibleMonth by remember {
        derivedStateOf { state.firstVisibleMonth.yearMonth }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(CalendarUtils.getPreviousMonth(visibleMonth))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous month"
                )
            }

            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = CalendarUtils.formatMonth(visibleMonth),
                fontSize = 18.sp
            )

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(CalendarUtils.getNextMonth(visibleMonth))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next month"
                )
            }
        }

        DaysOfWeekTitle(daysOfWeek = daysOfWeek)

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(day, isSelected = selectedDate.value == day.date) { day ->
                    selectedDate.value = day.date
                    onDateSelected(day.date)
                }
            },
        )
    }
}