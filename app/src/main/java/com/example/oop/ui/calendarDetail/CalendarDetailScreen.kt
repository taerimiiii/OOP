package com.example.oop.ui.calendarDetail

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
import com.example.oop.ui.calendarDetail.components.MedicineTakeCard
import com.example.oop.ui.calendarDetail.components.CalendarTitleCard
import com.example.oop.ui.calendarDetail.components.CalendarDetailTitle
import com.example.oop.ui.medicineDetail.MedicineDetailScreen
import java.time.LocalDate

@Composable
fun CalendarDetailScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: CalendarDetailViewModel = viewModel()
) {
    // selectedDate가 별경 될 때 마다 초기화
    LaunchedEffect(selectedDate) {
        viewModel.initialize(selectedDate)
    }

    // 뷰모델에서 읽기용 UI 받아오기.
    // by : 위임. 편집 권한?은 오른쪽에게 있음. 왼쪽은 빌려 쓰기만.
    val uiState by viewModel.uiState

    // 선택된 의약품이 있으면 MedicineDetailScreen 표시
    uiState.selectedMedicineId?.let { medicineId ->
        MedicineDetailScreen(
            medicineId = medicineId,
            onBackClick = {
                viewModel.handleEvent(CalendarDetailEvent.OnMedicineDetailBack)
            }
        )
        return
    }

    // 스크롤 객체
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, top = 1.dp, end = 5.dp, bottom = 17.dp)
            .verticalScroll(scroll),                // 세로 스크롤
        horizontalAlignment = Alignment.CenterHorizontally // 가운데 정렬
    ) {
        CalendarDetailTitle(onBackClick = onBackClick)
        
        CalendarTitleCard(selectedDate = selectedDate)

        // Favorite 리스트를 기반으로 MedicineTakeCard 생성
        // 즐겨찾기 목록은 뷰모델에서 갱신.
        for (favorite in uiState.favorites) {
            val medicine = uiState.medicines[favorite.itemSeq]
            val isTaken = uiState.medicineTakenStatus[favorite.itemSeq] ?: false   // 처음에는 당연히 약을 안 먹은 표시이니 null이고 그래서 false.
            
            MedicineTakeCard(
                medicine = medicine,
                isTaken = isTaken,
                onTakenChanged = { isTaken ->
                    viewModel.handleEvent(
                        CalendarDetailEvent.OnMedicineTakenChanged(favorite.itemSeq, isTaken)
                    )
                },
                // 추가기능
                onDetailClick = {
                    // itemSeq를 medicineId로 사용하여 상세 화면으로 이동
                    viewModel.handleEvent(CalendarDetailEvent.OnMedicineDetailClick(favorite.itemSeq))
                },
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}