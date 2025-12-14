package com.example.oop.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.oop.ui.calendar.components.CalendarTitleCard
import com.example.oop.ui.calendar.components.MoveDetailPageButton
import com.example.oop.ui.calendar.components.DateIntoBox
import com.example.oop.ui.calendar.components.MonthCalendar
import com.example.oop.ui.calendar.components.UserInfoBox
import com.example.oop.data.TempData

@Composable
fun CalendarScreen(
    modifier: Modifier,
    uiState: CalendarUiState,
    viewModel: CalendarViewModel,
    onMoveDetailClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp, top = 17.dp, end = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 타이틀
        CalendarTitleCard(text = "${TempData.user.userName ?: "사용자"} 님의 섭취 기록", height = 40.dp)

        // 월간 캘린더
        MonthCalendar(
            currentSeeMonth = uiState.currentSeeMonth, // 현재 월을 UI에 있는 현재 월로 갱신.
            selectedDate = uiState.selectedDate, // ViewModel에서 관리하는 선택된 날짜 전달
            onDateSelected = { date ->
                viewModel.handleEvent(CalendarEvent.OnDateSelected(date)) // 이벤트 실행
            },
            onPreviousMonth = {
                viewModel.handleEvent(CalendarEvent.OnPreviousMonth)
            },
            onNextMonth = {
                viewModel.handleEvent(CalendarEvent.OnNextMonth)
            }
        )
        
        // 하단 정보 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                modifier = Modifier.width(185.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 오늘의 날짜 박스
                DateIntoBox(
                    modifier = Modifier.zIndex(2f), // 출석 보다 앞에
                    title = "오늘의 날짜",
                    date = uiState.todayDate,
                )

                // 사용자 정보 박스
                UserInfoBox(
                    modifier = Modifier.zIndex(1f) // 오늘의 날짜 보다 뒤에
                        .offset(y = (-20).dp),
                    monthCount = uiState.monthCount,
                    lastMonthCount = uiState.lastMonthCount,
                    todayMedicineTaken = uiState.todayMedicineTaken,
                )
            }
            
            Column(
                modifier = Modifier.width(185.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 선택한 날짜 박스
                DateIntoBox(
                    modifier = Modifier,
                    title = "선택한 날짜",
                    date = uiState.selectedDate,
                )
                
                // 상세 화면으로 이동하는 버튼
                MoveDetailPageButton(
                    modifier = Modifier,
                    selectedDate = uiState.selectedDate,
                    onClick = onMoveDetailClick,
                )
            }
        }
    }
}
