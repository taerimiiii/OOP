package com.example.oop.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.ui.calendar.repository.CalendarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

//  ViewModel 상속
class CalendarViewModel(
    private val repository: CalendarRepository = CalendarRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private val userId = "current_user_id" // TODO: 실제 사용자 ID로 변경

    init {
        loadInitialData()
    }

    /**
     * 초기 데이터 로드
     */
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                val today = LocalDate.now()
                val currentMonth = CalendarUtils.formatYearMonth(CalendarUtils.getCurrentMonth())
                val lastMonth = CalendarUtils.formatYearMonth(CalendarUtils.getPreviousMonth(CalendarUtils.getCurrentMonth()))
                
                val monthlyAttendance = repository.getMonthlyAttendance(userId, currentMonth)
                val lastMonthAttendance = repository.getMonthlyAttendance(userId, lastMonth)
                val todayDateString = CalendarUtils.formatDate(today)
                val todayTakenMedicines = repository.getTakenMedicines(userId, todayDateString)
                
                _uiState.update {
                    it.copy(
                        todayDate = today,
                        monthlyAttendance = monthlyAttendance,
                        lastMonthAttendance = lastMonthAttendance,
                        todayMedicineTaken = todayTakenMedicines.isNotEmpty(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
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
