package com.example.oop.ui.calendarDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.calendar.CalendarTitleCard
import java.time.LocalDate

@Composable
fun CalendarDetailScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    BackHandler(onBack = onBack)
    Column(
        modifier = modifier.fillMaxSize().padding(start = 5.dp, top = 17.dp, end = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarTitleCard(text = "일일 복용 약 확인", height = 40.dp)
        WeekCalendar(targetDate = selectedDate)
        MedicineTakeCard(
            itemName = "가나릴정 [Ganaril Tab]",
            entpName = "영풍제약",
            chart = "흰색의 원형 구강붕해정제",
            itemClassName = "정제"
        )
        MedicineTakeCard(
            itemName = "가나릴정 [Ganaril Tab]",
            entpName = "영풍제약",
            chart = "흰색의 원형 구강붕해정제",
            itemClassName = "정제"
        )
    }
}