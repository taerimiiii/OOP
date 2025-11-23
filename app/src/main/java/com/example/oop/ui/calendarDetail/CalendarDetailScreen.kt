package com.example.oop.ui.calendarDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendar.components.CalendarTitleCard
import com.example.oop.ui.calendarDetail.components.MedicineTakeCard
import com.example.oop.ui.calendarDetail.components.WeekCalendar
import java.time.LocalDate

@Composable
fun CalendarDetailScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    viewModel: CalendarDetailViewModel = viewModel()
) {
    // selectedDate가 별경 될 때 마다 초기화
    LaunchedEffect(selectedDate) {
        viewModel.initialize(selectedDate)
    }

    // 뷰모델에서 읽기용 UI 받아오기.
    val uiState by viewModel.uiState

    // 스크롤 객체
    val scroll = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, top = 17.dp, end = 5.dp)
            .verticalScroll(scroll),                // 세로 스크롤
        horizontalAlignment = Alignment.CenterHorizontally // 가운데 정렬
    ) {
        CalendarTitleCard(text = "일일 복용 약 확인", height = 40.dp)
        
        WeekCalendar(targetDate = selectedDate)
        
        // Favorite 리스트를 기반으로 MedicineTakeCard 생성
        // 즐겨찾기 목록은 뷰모델에서 갱신.
        for (favorite in uiState.favorites) {
            val medicine = uiState.medicines[favorite.itemSeq]
            val isTaken = uiState.medicineTakenStatus[favorite.itemSeq] ?: false
//            val errorMessage = if (medicine == null) {
//                uiState.errorMessage ?: "의약품 정보를 찾을 수 없습니다"
//            } else {
//                null
//            }
            
            MedicineTakeCard(
                medicine = medicine,
                isTaken = isTaken,
                //errorMessage = errorMessage,
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