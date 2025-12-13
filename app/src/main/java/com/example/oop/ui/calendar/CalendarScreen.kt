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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.ui.calendar.components.CalendarTitleCard
import com.example.oop.ui.calendar.components.MoveDetailPageButton
import com.example.oop.ui.calendar.components.DateIntoBox
import com.example.oop.ui.calendar.components.MonthCalendar
import com.example.oop.ui.calendar.components.UserInfoBox
import com.example.oop.ui.calendarDetail.CalendarDetailScreen
import com.example.oop.data.TempData
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    val uiState by viewModel.uiState   // 뷰모델에서 읽기용 UI 받아오기.
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }   // 뷰모델이랑 똑같이 mutableStateOf 사용하기
    var gotoDetailScreen by remember { mutableStateOf(false) }
    val currentSelectedDate = selectedDate

    // if else 문으로 스크린 선택.
    if (gotoDetailScreen && currentSelectedDate != null) {  // !! 안 쓰기 위한 스마트캐스트
        CalendarDetailScreen(
            modifier = modifier,
            selectedDate = currentSelectedDate,
            onBackClick = { gotoDetailScreen = false }
        )
    } else {
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
                onDateSelected = { date ->
                    selectedDate = date     // 여기서 선택 날짜 갱신 후
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
                        onClick = {
                            gotoDetailScreen = true
                            viewModel.handleEvent(CalendarEvent.OnMoveDetailButton) // 연결은 해 뒀는데.. 결국 스크린 내에서 이벤트 처리 되는 중 ㅠ
                        },
                    )
                }
            }
        }
    }
}
