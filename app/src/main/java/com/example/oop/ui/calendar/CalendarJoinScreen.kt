package com.example.oop.ui.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendarDetail.CalendarDetailScreen

@Composable
fun CalendarJoinScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    val uiState by viewModel.uiState   // 뷰모델에서 읽기용 UI 받아오기.
    var gotoDetailScreen by remember { mutableStateOf(false) }

    // CalendarDetailScreen에서 돌아올 때 출석 횟수 갱신
    // LaunchedEffect : 컴포저블이 화면에 나타났을 때(또는 특정 값이 바뀔 때) 실행하기 위한 API
    LaunchedEffect(gotoDetailScreen) {
        if (!gotoDetailScreen) {
            // CalendarDetailScreen에서 CalendarScreen으로 돌아왔을 때
            viewModel.refreshCurrentMonthCount()
        }
    }

    // if else 문으로 스크린 선택.
    uiState.selectedDate?.let { selectedDate ->
        if (gotoDetailScreen) { // selectedDate가 null이 아니고 gotoDetailScreen이 true일 때
            CalendarDetailScreen(
                modifier = modifier,
                selectedDate = selectedDate,
                onBackClick = { gotoDetailScreen = false }
            )
        } else {                // selectedDate가 null이 아니고 gotoDetailScreen이 false일 때
            CalendarScreen(
                modifier = modifier,
                uiState = uiState,
                viewModel = viewModel,
                onMoveDetailClick = {
                    gotoDetailScreen = true
                    viewModel.handleEvent(CalendarEvent.OnMoveDetailButton)
                }
            )
        }
    } ?: CalendarScreen(    // selectedDate가 null 이면 CalendarScreen 호출
        modifier = modifier,
        uiState = uiState,
        viewModel = viewModel,
        onMoveDetailClick = {
            gotoDetailScreen = true
            viewModel.handleEvent(CalendarEvent.OnMoveDetailButton)
        }
    )
}
