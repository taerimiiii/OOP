package com.example.oop.ui.calendarDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.ui.calendarDetail.repository.CalendarDetailRepository
import com.example.oop.ui.calendarDetail.repository.TempData
import kotlinx.coroutines.launch
import java.time.LocalDate

// ViewModel 상속
class CalendarDetailViewModel(
    private val repository: CalendarDetailRepository = CalendarDetailRepository()
) : ViewModel() {
    
    // mutableStateOf 사용
    // mutableUiState는 내부에서만 수정 가능하고, uiState는 외부에서 읽기만 가능.
    private val mutableUiState = mutableStateOf(CalendarDetailUiState(selectedDate = LocalDate.now()))  // 임의 초기값. initialize()에서 selectedDate가 바로 설정됨.
    val uiState: State<CalendarDetailUiState> = mutableUiState

    private val uid = TempData.user.uid  // 일단 임시데이터에서 사용자 ID 가져오기

    // 선택된 날짜 설정 및 초기화
    fun initialize(selectedDate: LocalDate) {
        mutableUiState.value = mutableUiState.value.copy(selectedDate = selectedDate)
        loadFavorites() // 즐겨찾기 최신으로 갱신
    }

    // 즐겨찾기 의약품 목록 로드
    private fun loadFavorites() {
        viewModelScope.launch { // 네트워크(API) 통신을 위해 필요
            mutableUiState.value = mutableUiState.value.copy(errorMessage = null)
            
            try {
                val favorites = repository.getFavorites(uid)
                mutableUiState.value = mutableUiState.value.copy(favorites = favorites)
                
                // 각 의약품 정보 로드
                for (favorite in favorites) {
                    loadMedicine(favorite.itemSeq)
                    loadMedicineTakenStatus(favorite.itemSeq)
                }

            } catch (e: Exception) {
                mutableUiState.value = mutableUiState.value.copy(errorMessage = e.message)
            }
        }
    }

    // 의약품 정보 로드
    private fun loadMedicine(itemSeq: String) {
        viewModelScope.launch {
            val result = repository.getMedicine(itemSeq)    // getMedicine 호출

            if (result.isSuccess) {
                val medicine = result.getOrNull()           // 성공값(T) 또는 Null 반환
                if (medicine != null) {
                    mutableUiState.value = mutableUiState.value.copy(
                        medicines = mutableUiState.value.medicines + (itemSeq to medicine)
                    )
                }
            } else {
                val error = result.exceptionOrNull()
                if (error != null) {
                    mutableUiState.value = mutableUiState.value.copy(errorMessage = error.message)
                }
            }
        }
    }

    // 의약품 복용 상태 로드
    // 임시로 항상 false로 초기화 (DB 완성 전까지)
    private fun loadMedicineTakenStatus(itemSeq: String) {
        viewModelScope.launch {
            // DB 완성 전까지는 항상 false로 초기화
            mutableUiState.value = mutableUiState.value.copy(
                medicineTakenStatus = mutableUiState.value.medicineTakenStatus + (itemSeq to false)
            )
        }
    }

    // 이벤트 처리
    fun handleEvent(event: CalendarDetailEvent) {
        when (event) {
            is CalendarDetailEvent.OnMedicineTakenChanged -> {
                updateMedicineTakenStatus(event.itemSeq, event.isTaken)
            }
        }
    }

    // 의약품 복용 상태 업데이트
    // 로컬 상태만 업데이트 (DB 완성 전까지)
    private fun updateMedicineTakenStatus(itemSeq: String, isTaken: Boolean) {
        viewModelScope.launch {
            // DB 완성 전까지는 로컬 상태만 업데이트
            mutableUiState.value = mutableUiState.value.copy(
                medicineTakenStatus = mutableUiState.value.medicineTakenStatus + (itemSeq to isTaken)
            )
        }
    }
}
