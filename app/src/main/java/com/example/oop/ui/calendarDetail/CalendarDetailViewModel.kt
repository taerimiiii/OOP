package com.example.oop.ui.calendarDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.ui.calendarDetail.repository.CalendarDetailRepository
import com.example.oop.ui.calendarDetail.repository.FirebaseDebugHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

// ViewModel 상속
class CalendarDetailViewModel(
    private val repository: CalendarDetailRepository = CalendarDetailRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(
        CalendarDetailUiState(selectedDate = LocalDate.now())
    )
    val uiState: StateFlow<CalendarDetailUiState> = _uiState.asStateFlow()

    private val userId = "current_user_id" // TODO: 실제 사용자 ID로 변경

    /**
     * 선택된 날짜 설정 및 초기화
     */
    fun initialize(selectedDate: LocalDate) {
        _uiState.update { it.copy(selectedDate = selectedDate) }
        
        // 디버깅: DB 데이터 확인
        viewModelScope.launch {
            FirebaseDebugHelper.printCollectionStructure(userId)
            FirebaseDebugHelper.printAllFavorites(userId)
            FirebaseDebugHelper.printAllDailyLogs(userId)
        }
        
        loadFavorites()
    }

    /**
     * 즐겨찾기 의약품 목록 로드
     */
    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                val favorites = repository.getFavorites(userId)
                _uiState.update { it.copy(favorites = favorites) }
                
                // 각 의약품 정보 로드
                favorites.forEach { favorite ->
                    loadMedicine(favorite.itemSeq)
                    loadMedicineTakenStatus(favorite.itemSeq)
                }
                
                _uiState.update { it.copy(isLoading = false) }
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
     * 의약품 정보 로드
     */
    private fun loadMedicine(itemSeq: String) {
        viewModelScope.launch {
            repository.getMedicine(itemSeq).onSuccess { medicine ->
                _uiState.update { state ->
                    state.copy(
                        medicines = state.medicines + (itemSeq to medicine)
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(errorMessage = error.message)
                }
            }
        }
    }

    /**
     * 의약품 복용 상태 로드
     */
    private fun loadMedicineTakenStatus(itemSeq: String) {
        viewModelScope.launch {
            val dateString = CalendarDetailUtils.formatDate(_uiState.value.selectedDate)
            val isTaken = repository.getMedicineTakenStatus(userId, itemSeq, dateString)
            
            _uiState.update { state ->
                state.copy(
                    medicineTakenStatus = state.medicineTakenStatus + (itemSeq to isTaken)
                )
            }
        }
    }

    /**
     * 이벤트 처리
     */
    fun handleEvent(event: CalendarDetailEvent) {
        when (event) {
            is CalendarDetailEvent.OnMedicineTakenChanged -> {
                updateMedicineTakenStatus(event.itemSeq, event.isTaken)
            }
            is CalendarDetailEvent.OnImageDialogClicked -> {
                _uiState.update {
                    it.copy(
                        showImageDialog = true,
                        selectedImageItemSeq = event.itemSeq
                    )
                }
            }
            is CalendarDetailEvent.OnImageDialogDismissed -> {
                _uiState.update {
                    it.copy(
                        showImageDialog = false,
                        selectedImageItemSeq = null
                    )
                }
            }
        }
    }

    /**
     * 의약품 복용 상태 업데이트
     */
    private fun updateMedicineTakenStatus(itemSeq: String, isTaken: Boolean) {
        viewModelScope.launch {
            val dateString = CalendarDetailUtils.formatDate(_uiState.value.selectedDate)
            
            repository.updateMedicineTakenStatus(userId, itemSeq, dateString, isTaken)
            
            _uiState.update { state ->
                state.copy(
                    medicineTakenStatus = state.medicineTakenStatus + (itemSeq to isTaken)
                )
            }
        }
    }
}
