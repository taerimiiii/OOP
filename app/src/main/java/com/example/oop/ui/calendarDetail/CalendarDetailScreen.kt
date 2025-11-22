package com.example.oop.ui.calendarDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendar.CalendarTitleCard
import com.example.oop.ui.calendarDetail.components.MedicineTakeCard
import com.example.oop.ui.calendarDetail.components.WeekCalendar
import java.time.LocalDate

@Composable
fun CalendarDetailScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    viewModel: CalendarDetailViewModel = viewModel()
) {
    BackHandler(onBack = onBack)
    
    // 선택된 날짜로 초기화
    androidx.compose.runtime.LaunchedEffect(selectedDate) {
        viewModel.initialize(selectedDate)
    }
    
    val uiState by viewModel.uiState.collectAsState()
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
        uiState.favorites.forEach { favorite ->
            val medicine = uiState.medicines[favorite.itemSeq]
            val isTaken = uiState.medicineTakenStatus[favorite.itemSeq] ?: false
            val isLoading = uiState.isLoading && medicine == null
            val errorMessage = if (medicine == null && !uiState.isLoading) {
                uiState.errorMessage ?: "의약품 정보를 찾을 수 없습니다"
            } else {
                null
            }
            
            MedicineTakeCard(
                medicine = medicine,
                isTaken = isTaken,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onTakenChanged = { isTaken ->
                    viewModel.handleEvent(
                        CalendarDetailEvent.OnMedicineTakenChanged(favorite.itemSeq, isTaken)
                    )
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}