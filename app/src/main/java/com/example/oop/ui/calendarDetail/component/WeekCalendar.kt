package com.example.oop.ui.calendarDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendar.CalendarUtils
import com.example.oop.ui.calendar.displayText
import com.example.oop.ui.calendarDetail.component.DayUiModel
import com.example.oop.ui.calendarDetail.component.WeekDayCard
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun WeekCalendar(
    targetDate: LocalDate,
    selectedDate: LocalDate = targetDate,
    modifier: Modifier = Modifier.Companion,
    onDateSelected: (LocalDate) -> Unit = {},
) {
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)

    val targetMonth = remember(targetDate) { YearMonth.from(targetDate) }
    val startDate = remember(targetMonth) { targetMonth.atDay(1) }
    val endDate = remember(targetMonth) { targetMonth.atEndOfMonth() }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selectedDate,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(whiteColor)
            .padding(vertical = 8.dp),
    ) {
        Text(
            modifier = Modifier.Companion
                .align(Alignment.Companion.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = targetMonth.displayText(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Companion.SemiBold,
            color = blackColor,
        )

        CompositionLocalProvider(LocalContentColor provides blackColor) {
            WeekCalendar(
                modifier = Modifier.Companion.padding(vertical = 4.dp),
                state = state,
                calendarScrollPaged = false,
                dayContent = { day ->
                    val isInTargetMonth = YearMonth.from(day.date) == targetMonth
                    val model = DayUiModel(
                        date = day.date,
                        dayText = CalendarUtils.formatDayNumber(day.date),
                        weekDayText = day.date.dayOfWeek.displayText(short = true),
                        isCurrentMonth = isInTargetMonth,
                        isSelected = selectedDate == day.date && isInTargetMonth,
                    )
                    WeekDayCard(
                        model = model,
                        onClick = onDateSelected,
                    )
                },
            )
        }
    }
}