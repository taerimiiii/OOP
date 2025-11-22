package com.example.oop.ui.calendarDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.WeekCalendar as KizWeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.year} ${this.month.displayText(short = short)}"
}

fun Month.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}

fun DayOfWeek.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}

@Composable
fun WeekCalendar(
    targetDate: LocalDate,
) {
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)

    val targetMonth = remember(targetDate) { YearMonth.from(targetDate) }
    val startDate = remember(targetMonth) { targetMonth.atDay(1) }
    val endDate = remember(targetMonth) { targetMonth.atEndOfMonth() }
    var selection by remember(targetDate) { mutableStateOf(targetDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(whiteColor)
            .padding(vertical = 8.dp),
    ) {
        val state = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = targetDate,
        )

        // 주간 캘린더 타이틀
        val visibleYearMonth = remember(targetMonth) { targetMonth }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 4.dp, top = 10.dp),
            text = visibleYearMonth.displayText(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = blackColor,
        )

        // 주간 캘린더 날짜 카드 영역
        CompositionLocalProvider(LocalContentColor provides blackColor) {
            KizWeekCalendar(
                modifier = Modifier.padding(vertical = 4.dp),
                state = state,
                calendarScrollPaged = false,
                dayContent = { day ->
                    val isInTargetMonth = YearMonth.from(day.date) == targetMonth
                    Day(
                        date = day.date,
                        isCurrentMonth = isInTargetMonth,
                        selected = selection == day.date && isInTargetMonth,
                    ) { clickedDate ->
                        if (isInTargetMonth) {
                            selection = clickedDate
                        }
                    }
                },
            )
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(
    date: LocalDate,
    isCurrentMonth: Boolean,
    selected: Boolean = false,
    onClick: (LocalDate) -> Unit = {},
) {
    val greenColor = Color(0xFF71E000)
    val lightGreenColor = Color(0xFFE1EFC7)

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenWidth = remember(windowInfo.containerSize.width, density) {
        with(density) { windowInfo.containerSize.width.toDp() }
    }
    Box(
        modifier = Modifier
            .width(screenWidth / 7)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = if (isCurrentMonth) lightGreenColor else Color.Transparent)
            .border(
                shape = RoundedCornerShape(8.dp),
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) greenColor else Color.Transparent,
            )
            .wrapContentHeight()
            .clickable(enabled = isCurrentMonth) { onClick(date) }
            .alpha(if (isCurrentMonth) 1f else 0f),
        contentAlignment = Alignment.Center,
    ) {
        if (isCurrentMonth) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = dateFormatter.format(date),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = date.dayOfWeek.displayText().take(3),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}

