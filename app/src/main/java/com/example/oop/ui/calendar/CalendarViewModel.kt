package com.example.oop.ui.calendar

import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * 월간/주간 캘린더 모두의 상태를 단일 진실 소스로 관리하는 뷰모델.
 * UI는 [uiState] 를 구독하고, 상호작용은 [onEvent] 로 전달한다.
 */
class CalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    /**
     * UI에서 발생한 이벤트를 받아 상태를 갱신한다.
     */
    fun onEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.DateSelected -> handleDateSelected(event.date)
            is CalendarEvent.VisibleMonthChanged -> handleVisibleMonthChanged(event.month)
            CalendarEvent.ResetSelection -> clearSelection()
        }
    }

    /**
     * 날짜가 선택되면 선택 상태와 가시 월을 함께 갱신.
     */
    private fun handleDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = date,
                visibleMonth = YearMonth.from(date),
            )
        }
    }

    /**
     * 월간 캘린더 스크롤 등으로 가시 월이 달라졌을 때 호출.
     */
    private fun handleVisibleMonthChanged(month: YearMonth) {
        _uiState.update { current ->
            if (current.visibleMonth == month) {
                current
            } else {
                current.copy(visibleMonth = month)
            }
        }
    }

    /**
     * 주간 상세 화면에서 뒤로 가기를 눌렀을 때 선택 상태를 해제.
     */
    private fun clearSelection() {
        _uiState.update { it.copy(selectedDate = null) }
    }
}