package com.example.oop.ui.calendarDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.ui.calendarDetail.repository.CalendarDetailRepository
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
     * 임시로 항상 false로 초기화 (DB 완성 전까지)
     */
    private fun loadMedicineTakenStatus(itemSeq: String) {
        viewModelScope.launch {
            // DB 완성 전까지는 항상 false로 초기화
            _uiState.update { state ->
                state.copy(
                    medicineTakenStatus = state.medicineTakenStatus + (itemSeq to false)
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
     * 로컬 상태만 업데이트 (DB 완성 전까지)
     */
    private fun updateMedicineTakenStatus(itemSeq: String, isTaken: Boolean) {
        viewModelScope.launch {
            // DB 완성 전까지는 로컬 상태만 업데이트
            _uiState.update { state ->
                state.copy(
                    medicineTakenStatus = state.medicineTakenStatus + (itemSeq to isTaken)
                )
            }
        }
    }
}
