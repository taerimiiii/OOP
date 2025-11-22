package com.example.oop.ui.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.oop.ui.calendar.components.CheckButton
import com.example.oop.ui.calendar.components.DateInfoBox
import com.example.oop.ui.calendar.components.MonthCalendar
import com.example.oop.ui.calendar.components.UserInfoBox
import com.example.oop.ui.calendarDetail.CalendarDetailScreen
import com.example.oop.ui.calendarDetail.repository.TempData
import java.time.LocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDetailScreen by remember { mutableStateOf(false) }

    if (showDetailScreen && selectedDate != null) {
        CalendarDetailScreen(
            modifier = modifier,
            selectedDate = selectedDate!!,
            onBack = { showDetailScreen = false }
        )
    } else {
        Column(
            modifier = modifier.fillMaxSize().padding(start = 5.dp, top = 17.dp, end = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarTitleCard(text = "${TempData.user.userName ?: "사용자"} 님의 섭취 기록", height = 40.dp)
            MonthCalendar(
                onDateSelected = { date ->
                    selectedDate = date
                    viewModel.handleEvent(CalendarEvent.OnDateSelected(date))
                }
            )
            
            // 날짜 정보 박스들
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.width(185.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 오늘의 날짜 박스 (앞으로 보내기)
                    DateInfoBox(
                        title = "오늘의 날짜",
                        date = uiState.todayDate,
                        modifier = Modifier
                            .width(185.dp)
                            .height(80.dp)
                            .zIndex(2f)
                    )

                    // 사용자 정보 박스 (뒤로 보내기)
                    UserInfoBox(
                        monthlyAttendance = uiState.monthlyAttendance,
                        lastMonthAttendance = uiState.lastMonthAttendance,
                        todayMedicineTaken = uiState.todayMedicineTaken,
                        modifier = Modifier
                            .width(185.dp)
                            .height(350.dp)
                            .offset(y = (-20).dp)
                            .zIndex(1f)
                    )
                }
                
                Column(
                    modifier = Modifier.width(185.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 선택한 날짜 박스
                    DateInfoBox(
                        title = "선택한 날짜",
                        date = uiState.selectedDate,
                        modifier = Modifier
                            .width(185.dp)
                            .height(80.dp)
                    )
                    
                    // 상세 화면으로 이동하는 버튼
                    CheckButton(
                        selectedDate = uiState.selectedDate,
                        onClick = {
                            showDetailScreen = true
                            viewModel.handleEvent(CalendarEvent.OnCheckButtonClicked)
                        },
                        modifier = Modifier
                            .width(170.dp)
                            .height(200.dp)
                            .padding(vertical = 20.dp)
                    )
                }
            }
        }
    }
}
