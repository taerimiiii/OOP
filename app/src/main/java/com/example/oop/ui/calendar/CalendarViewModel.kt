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

    // 특정 월의 출석 횟수 계산 함수 (복용한 날짜 수)
    private fun calculateMonthCount(yearMonth: YearMonth): Int {
        val monthString = CalendarUtils.formatYearMonth(yearMonth)
        return TempData.logs.count { log ->
            log.date.startsWith(monthString) && log.items.values.any { it.taken }
        }
    }

    // 현재 월과 지난달 출석 횟수 계산 함수
    private fun calculateMonthCounts(yearMonth: YearMonth): Pair<Int, Int> {
        val monthCount = calculateMonthCount(yearMonth)
        val lastMonthCount = calculateMonthCount(CalendarUtils.getPreviousMonth(yearMonth))
        return Pair(monthCount, lastMonthCount)
    }

    // 오늘 복용 여부 계산 함수
    private fun calculateTodayMedicineTaken(): Boolean {
        val today = LocalDate.now()
        val todayDateString = CalendarUtils.formatDate(today)
        val todayLog = TempData.logs.find { it.date == todayDateString }
        return todayLog?.items?.values?.any { it.taken } ?: false
    }

    // 초기값 설정
    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val today = LocalDate.now()
        val currentYearMonth = CalendarUtils.getCurrentMonth() // YearMonth 타입

        // 현재 월 출석 횟수, 지난달 출석 횟수 계산
        val (monthCount, lastMonthCount) = calculateMonthCounts(currentYearMonth)
        // 오늘 복용 여부 확인
        val todayMedicineTaken = calculateTodayMedicineTaken()
        
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
        val (monthCount, lastMonthCount) = calculateMonthCounts(newMonth)
        
        mutableUiState.value = mutableUiState.value.copy(
            currentSeeMonth = newMonth,
            monthCount = monthCount,
            lastMonthCount = lastMonthCount
        )
    }
    
    // 현재 보이는 월의 출석 횟수 갱신 (CalendarDetailScreen에서 복용 기록 추가 후 호출)
    fun refreshCurrentMonthCount() {
        val currentSeeMonth = mutableUiState.value.currentSeeMonth
        val (monthCount, lastMonthCount) = calculateMonthCounts(currentSeeMonth)
        val todayMedicineTaken = calculateTodayMedicineTaken()
        
        mutableUiState.value = mutableUiState.value.copy(
            monthCount = monthCount,
            lastMonthCount = lastMonthCount,
            todayMedicineTaken = todayMedicineTaken
        )
    }
}
