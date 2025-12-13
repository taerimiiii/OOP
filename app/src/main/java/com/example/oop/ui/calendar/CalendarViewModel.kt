package com.example.oop.ui.calendar

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.oop.data.TempData
import java.time.LocalDate
import java.time.YearMonth

// ViewModel 상속
// 화면에 나타낼 UI 상태 관리.
class CalendarViewModel : ViewModel() {
    
    // mutableStateOf 사용 (블로그에서 찾아본 StateFlow 너무 어려워서..)
    // useState와 비슷. 상태가 변경되면 리랜더링한다고 생각.
    // mutableUiState는 내부에서만 수정 가능하고, uiState는 외부에서 읽기만 가능.
    private val mutableUiState = mutableStateOf(CalendarUiState())
    val uiState: State<CalendarUiState> = mutableUiState    // Screen 에서 읽어서 사용함.

    init {
        loadInitialData()
    }

    // 하단 영역 하드 코딩.
    private fun loadInitialData() {
        val today = LocalDate.now()
        val todayDateString = CalendarUtils.formatDate(today)
        val currentYearMonth = CalendarUtils.getCurrentMonth() // YearMonth 타입
        val currentMonth = CalendarUtils.formatYearMonth(currentYearMonth) // String 타입 (출석 계산용)
        val lastMonth = CalendarUtils.formatYearMonth(CalendarUtils.getPreviousMonth(currentYearMonth))
        
        // 오늘 복용 여부 확인
        val todayLog = TempData.logs.find { it.date == todayDateString }
        val todayMedicineTaken = todayLog?.items?.values?.any { it.taken } ?: false
        
        // 현재 월 출석 횟수 계산 (복용한 날짜 수)
        // 업뎃 로직이랑 겹치니까 나중에 하나로 합치기.
        val monthCount = TempData.logs.count { log ->
            log.date.startsWith(currentMonth) && log.items.values.any { it.taken }
        }
        
        // 지난달 출석 횟수 계산
        val lastMonthCount = TempData.logs.count { log ->
            log.date.startsWith(lastMonth) && log.items.values.any { it.taken }
        }
        
        // mutableStateOf는 .value로 접근하고 직접 할당
        mutableUiState.value = mutableUiState.value.copy(
            todayDate = today,
            currentSeeMonth = currentYearMonth, // 현재 보이는 월 초기화
            monthCount = monthCount,
            lastMonthCount = lastMonthCount,
            todayMedicineTaken = todayMedicineTaken,
            isLoading = false,
            errorMessage = null
        )
    }

    // 이벤트 처리 핸들
    fun handleEvent(event: CalendarEvent) {
        when (event) {
            // 날짜 선택 처리
            is CalendarEvent.OnDateSelected -> {
                mutableUiState.value = mutableUiState.value.copy(selectedDate = event.date)
            }

            is CalendarEvent.OnMoveDetailButton -> {
                // CalendarDetailScreen으로 이동하는 것은 Screen에서 처리 중. 이것도 여기서 처리하게 바꿔보기.
            }

            // 월 이전으로 변경 처리
            is CalendarEvent.OnPreviousMonth -> {
                val previousMonth = CalendarUtils.getPreviousMonth(mutableUiState.value.currentSeeMonth)
                updateMonthCount(previousMonth)
            }

            // 월 다음으로 변경 처리
            is CalendarEvent.OnNextMonth -> {
                val nextMonth = CalendarUtils.getNextMonth(mutableUiState.value.currentSeeMonth)
                updateMonthCount(nextMonth)
            }
        }
    }
    
    // 월이 변경될 때 출석 횟수 재계산
    private fun updateMonthCount(newMonth: YearMonth) {
        val currentMonth = CalendarUtils.formatYearMonth(newMonth)
        val lastMonth = CalendarUtils.formatYearMonth(CalendarUtils.getPreviousMonth(newMonth))
        
        // 새로운 월의 출석 횟수 계산
        val monthCount = TempData.logs.count { log ->
            log.date.startsWith(currentMonth) && log.items.values.any { it.taken }
        }
        
        val lastMonthCount = TempData.logs.count { log ->
            log.date.startsWith(lastMonth) && log.items.values.any { it.taken }
        }
        
        mutableUiState.value = mutableUiState.value.copy(
            currentSeeMonth = newMonth,
            monthCount = monthCount,
            lastMonthCount = lastMonthCount
        )
    }
}
