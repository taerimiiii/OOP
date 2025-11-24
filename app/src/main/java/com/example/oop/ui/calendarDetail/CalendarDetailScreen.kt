package com.example.oop.ui.calendarDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.data.model.Favorite
import com.example.oop.ui.calendar.CalendarTitleCard
import java.time.LocalDate

@Composable
fun CalendarDetailScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    BackHandler(onBack = onBack)
    
    // 임시 Favorite 리스트 (실제로는 DB나 ViewModel에서 가져와야 함)
    val favorites = remember {
        listOf(
            Favorite(itemSeq = "200808876"),
            Favorite(itemSeq = "200808877")
        )
    }
    
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, top = 17.dp, end = 5.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarTitleCard(text = "일일 복용 약 확인", height = 40.dp)
        WeekCalendar(targetDate = selectedDate)
        
        // Favorite 리스트를 기반으로 MedicineTakeCard 생성
        favorites.forEach { favorite ->
            MedicineTakeCard(
                itemSeq = favorite.itemSeq,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}