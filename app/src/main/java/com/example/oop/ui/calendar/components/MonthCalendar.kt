package com.example.oop.ui.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.rememberCoroutineScope
import com.example.oop.data.TempData
import com.example.oop.ui.calendar.CalendarUtils
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.launch

@Composable
fun MonthCalendar(
    modifier: Modifier = Modifier,
    currentSeeMonth: YearMonth,     // ViewModel에서 관리하는 현재 월
    selectedDate: LocalDate? = null, // 외부에서 선택된 날짜 전달받기
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,    // 컴포넌트 사용 할 때 뷰모델로 이전 월 클릭 이벤트 시 상태 갱신되게 하기.
    onNextMonth: () -> Unit,        // 컴포넌트 사용 할 때 뷰모델로 다음 월 클릭 이벤트 시 상태 갱신되게 하기.
) {
    // remember = 상태 저장. Composable 함수가 다시 호출되더라도 값이 재생성X
    //원래 샘플 코드
    // remember(key) 형태: key가 변경될 때만 재계산됨
    val preMonth = remember(currentSeeMonth) { currentSeeMonth.minusMonths(1) }  // currentSeeMonth가 바뀌면 재계산.
    val nextMonth = remember(currentSeeMonth) { currentSeeMonth.plusMonths(1) }     // 빠릿빠릿하게 로딩되어서 좋구만요
    val daysOfWeek = remember { daysOfWeek() }
    val internalSelectedDate = remember { mutableStateOf<LocalDate?>(null) }                     // mutableStateOf : 반응형 UI. 초기값 null인데 외부에서 전달받은 날짜 있으면 그 날짜로 바꿈.
    val coroutineScope = rememberCoroutineScope()                                                       // 애니메이션. 캘린더 라이브러리 사용한 인도형님 코드 훔쳐서 따라함.
    
    // 외부에서 전달받은 selectedDate가 있으면 내부 상태와 동기화
    val currentSelectedDate = selectedDate ?: internalSelectedDate.value

    // state = 캘린더 상태 객체
    // rememberCalendarState : 라이브러리 함수, 파라미터 이름 변경 불가.
    val state = rememberCalendarState(
        startMonth = preMonth,              // 이전 월
        endMonth = nextMonth,               // 다음 월
        firstVisibleMonth = currentSeeMonth,// ViewModel에서 관리하는 현재 월
        firstDayOfWeek = daysOfWeek.first() // 주의 시작 요일
    )

    Column(modifier = modifier) {

        // 타이틀 밑, 캘린더 상단 영역.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이전 버튼
            IconButton(
                onClick = {
                    coroutineScope.launch {                             // 부드러운 애니메이션
                        state.animateScrollToMonth(state.startMonth)    // UI 애니메이션
                        onPreviousMonth()                               // UI상태 업뎃을 위한 호출
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous month"
                )
            }

            Text(
                modifier = Modifier.width(310.dp),
                textAlign = TextAlign.Center,
                text = CalendarUtils.formatOutYearMonth(currentSeeMonth), // 현재 월
                fontSize = 18.sp
            )

            // 다음 버튼
            IconButton(
                onClick = {
                    coroutineScope.launch {                             // 부드러운 애니메이션
                        state.animateScrollToMonth(state.endMonth)      // UI 애니메이션
                        onNextMonth()                                   // UI상태 업뎃을 위한 호출
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next month"
                )
            }
        }

        // 일월화수목금토
        // daysOfWeek : 라이브러리. 라이브러리 자체가 일요일부터 시작해서 별다른 수정 없이 그대로 사용.
        DaysOfWeekTitle(daysOfWeek = daysOfWeek)

        // 샘플 코드
        // HorizontalCalendar : 라이브러리. 달력의 전체 뼈대 그려준다.
        HorizontalCalendar(
            state = state,  // 캘린더 상태 업뎃

            // 날짜 칸은 dayContent으로 지정.
            // day.date는 LocalDate. ex) “11월 2일” 칸을 누르면 → day.date = LocalDate(2025, 11, 2)
            // day는 CalendarDay이다. 그냥 이렇게 생각하면 편함. 라이브러리가 각 날짜마다 CalendarDay 객체를 생성해 dayContent 람다에 전달하는 중.
            dayContent = { day ->   // 날짜 칸을 다
                // 해당 날짜에 복용 기록이 있는지 확인
                val dateString = CalendarUtils.formatDate(day.date) // 지금 그릴 날짜
                val log = TempData.logs.find { it.date == dateString }  // 지금 그릴 날짜의 로그(복용 기록)
                val hasLog = log?.items?.values?.any { it.taken } ?: false  // 복용 기록이 있으면 true
                
                Day(
                    day,
                    isSelected = currentSelectedDate == day.date, // 선택한 날짜 == 지금 날자이면 Day 함수 부분에서 초록 칠하기.
                    hasLog = hasLog // 복용 기록이 있으면 연한 초록색 표시
                ) { day ->  // onClick 람다 실행.(Day 함수의 세번째 인자, CalendarDay타입(day)을 넘여서 반환값 없음)
                    internalSelectedDate.value = day.date   // 선택한 날짜 갱신 (내부만 업뎃)
                    onDateSelected(day.date)        // CalendarScreen(부모)에 선택날짜 넘기기 (onDateSelected는 부모의 매개변수)
                }
            },
        )
    }
}

// Day 함수 : 샘플코드
// CalendarDay : 라이브러리
@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, hasLog: Boolean, onClick: (CalendarDay) -> Unit) {
    val blackColor = Color(0xFF000000)
    val grayColor = Color(0xFF808080)
    val greenColor = Color(0xFF71E000)
    val lightGreenColor = Color(0xFFD6F4B6)

    // day.position : InDate, OutDate 구분. +MonthDate
    // DayPosition : 라이브러리. InDate, MonthDate, OutDate 있음.
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = if (isSelected) {
                                    greenColor
                                } else if (hasLog) {
                                    lightGreenColor
                                } else {
                                    Color.Transparent // 투명배경
                                }
            )
            .clickable( // 날짜 클릭 시작!
                enabled = day.position == DayPosition.MonthDate,    // 현재 월만 클릭 가능
                onClick = { onClick(day) }                          // onClick 람다
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (day.position == DayPosition.MonthDate) { // 이전 월이랑 다음 월은 회색, 현재 월은 까만색
                        blackColor
                    } else {
                        grayColor
                    }
        )
    }
}

// 샘플코드(일월화수목금토)
@Composable
private fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                color = Color.Gray
            )
        }
    }
}

