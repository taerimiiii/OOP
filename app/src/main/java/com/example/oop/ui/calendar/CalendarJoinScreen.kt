package com.example.oop.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendarDetail.CalendarDetailScreen

@Composable
fun CalendarJoinScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    // 뷰모델에서 읽기용 UI 받아오기.
    // by : 위임. 편집 권한?은 오른쪽에게 있음. 왼쪽은 빌려 쓰기만.
    val uiState by viewModel.uiState

    // if else 문으로 스크린 선택.
    uiState.selectedDate?.let { selectedDate ->
        if (uiState.gotoDetailScreen) { // selectedDate가 null이 아니고 gotoDetailScreen이 true일 때
            CalendarDetailScreen(
                modifier = modifier,
                selectedDate = selectedDate,
                onBackClick = {
                    viewModel.handleEvent(CalendarEvent.OnDetailScreenBack)
                }
            )
        } else {                // selectedDate가 null이 아니고 gotoDetailScreen이 false일 때
            CalendarScreen(
                modifier = modifier,
                uiState = uiState,
                viewModel = viewModel,
                onMoveDetailClick = {
                    viewModel.handleEvent(CalendarEvent.OnMoveDetailButton)
                }
            )
        }
    } ?: CalendarScreen(    // selectedDate가 null 이면 CalendarScreen 호출
        modifier = modifier,
        uiState = uiState,
        viewModel = viewModel,
        onMoveDetailClick = {
            viewModel.handleEvent(CalendarEvent.OnMoveDetailButton)
        }
    )
}
