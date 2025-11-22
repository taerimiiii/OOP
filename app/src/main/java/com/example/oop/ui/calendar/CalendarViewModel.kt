package com.example.oop.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.oop.ui.calendarDetail.repository.TempData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

//  ViewModel 상속
class CalendarViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    /**
     * 초기 데이터 로드
     * 임시 데이터 사용 (DB 완성 전까지)
     */
    private fun loadInitialData() {
        val today = LocalDate.now()
        val todayDateString = CalendarUtils.formatDate(today)
        val currentMonth = CalendarUtils.formatYearMonth(CalendarUtils.getCurrentMonth())
        val lastMonth = CalendarUtils.formatYearMonth(CalendarUtils.getPreviousMonth(CalendarUtils.getCurrentMonth()))
        
        // 오늘 복용 여부 확인
        val todayLog = TempData.logs.find { it.date == todayDateString }
        val todayMedicineTaken = todayLog?.items?.values?.any { it.taken } ?: false
        
        // 현재 월 출석 횟수 계산 (복용한 날짜 수)
        val monthlyAttendance = TempData.logs.count { log ->
            log.date.startsWith(currentMonth) && log.items.values.any { it.taken }
        }
        
        // 지난달 출석 횟수 계산
        val lastMonthAttendance = TempData.logs.count { log ->
            log.date.startsWith(lastMonth) && log.items.values.any { it.taken }
        }
        
        _uiState.update {
            it.copy(
                todayDate = today,
                monthlyAttendance = monthlyAttendance,
                lastMonthAttendance = lastMonthAttendance,
                todayMedicineTaken = todayMedicineTaken,
                isLoading = false,
                errorMessage = null
            )
        }
    }

    /**
     * 이벤트 처리
     */
    fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnDateSelected -> {
                _uiState.update { it.copy(selectedDate = event.date) }
            }
            is CalendarEvent.OnCheckButtonClicked -> {
                // CalendarDetailScreen으로 이동하는 것은 Screen에서 처리
            }
            is CalendarEvent.OnPreviousMonth -> {
                // MonthCalendar에서 직접 처리
            }
            is CalendarEvent.OnNextMonth -> {
                // MonthCalendar에서 직접 처리
            }
        }
    }
}
