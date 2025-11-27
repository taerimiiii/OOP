package com.example.oop.ui.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.calendarDetail.CalendarDetailScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// UI 화면을 구성하는 Composable 함수
@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
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
            CalendarTitleCard(text = "약먹자 님의 섭취 기록", height = 40.dp) // 사용자 이름으로 변경 필요
            MonthCalendar(
                onDateSelected = { date -> selectedDate = date }
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
                        date = LocalDate.now(),
                        modifier = Modifier
                            .width(185.dp)
                            .height(80.dp)
                            .zIndex(2f)  // UserInfoBox보다 앞에 표시
                    )

                    // 사용자 정보 박스 (뒤로 보내기)
                    UserInfoBox(
                        modifier = Modifier
                            .width(185.dp)
                            .height(350.dp)
                            .offset(y = (-20).dp)  // 위로 20.dp 이동하여 겹치게
                            .zIndex(1f)  // DateInfoBox보다 뒤에 표시
                    )
                }
                
                Column(
                    modifier = Modifier.width(185.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 선택한 날짜 박스
                    DateInfoBox(
                        title = "선택한 날짜",
                        date = selectedDate,
                        modifier = Modifier
                            .width(185.dp)
                            .height(80.dp)
                    )
                    
                    // 상세 화면으로 이동하는 버튼
                    CheckButton(
                        selectedDate = selectedDate,
                        onClick = { showDetailScreen = true },
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

// 날짜 정보를 표시하는 박스 컴포넌트
@Composable
private fun DateInfoBox(
    title: String,
    date: LocalDate?,
    modifier: Modifier = Modifier
) {
    val borderColor = Color(0xFFD6F4B6)
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)
    val dateFormatter = remember { DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault()) }
    
    Box(
        modifier = modifier
            .border(
                border = BorderStroke(3.dp, borderColor),
                shape = RoundedCornerShape(percent = 30)
            )
            .background(
                color = whiteColor,
                shape = RoundedCornerShape(percent = 30)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = blackColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (date != null) dateFormatter.format(date) else "없음",
                fontSize = 18.sp,
                color = blackColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
    }
}

// 사용자 정보를 표시하는 박스 컴포넌트
@Composable
private fun UserInfoBox(
    modifier: Modifier = Modifier
) {
    val borderColor = Color(0xFFD6F4B6)
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)

    Box(
        modifier = modifier
            .border(
                border = BorderStroke(3.dp, borderColor),
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 0,
                    bottomStartPercent = 30,
                    bottomEndPercent = 30
                )
            )
            .background(
                color = whiteColor,
                shape = RoundedCornerShape(
                    topStartPercent = 0,
                    topEndPercent = 0,
                    bottomStartPercent = 30,
                    bottomEndPercent = 30
                )
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            //modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "월간 출석 : 00회",
                fontSize = 18.sp,
                color = blackColor
            )
            Text(
                text = "지난달 출석 : 00회",
                fontSize = 18.sp,
                color = blackColor
            )
            Text(
                text = "오늘의 약 복용 : X",
                fontSize = 18.sp,
                color = blackColor
            )
        }
    }
}

// 복용 체크하기 버튼 컴포넌트
@Composable
private fun CheckButton(
    selectedDate: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("M월 d일", Locale.getDefault()) }
    val blackColor = Color(0xFF000000)
    val greenColor = Color(0xFF71E000)
    val darkGrayColor = Color(0xFFD9D9D9)
    val whiteColor = Color(0xFFFFFFFF)

    Button(
        onClick = onClick,
        enabled = selectedDate != null,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = greenColor,
            contentColor = whiteColor,
            disabledContainerColor = darkGrayColor,
            disabledContentColor = blackColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedDate != null) {
                Text(
                    text = dateFormatter.format(selectedDate),
                    fontSize = 18.sp,
                    color = blackColor,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
            Text(
                text = "복용 체크하기",
                fontSize = 18.sp,
                color = blackColor
            )
        }
    }
}