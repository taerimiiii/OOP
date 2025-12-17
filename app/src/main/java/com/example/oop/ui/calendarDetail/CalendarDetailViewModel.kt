package com.example.oop.ui.calendarDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.data.model.DailyLog
import com.example.oop.data.model.DailyLogItem
import com.example.oop.data.repository.CalendarDetailRepository
import com.example.oop.data.TempData
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
    private var previousSelectedDate: LocalDate? = null  // 이전 선택 날짜 추적

    // 초기화
    fun initialize(selectedDate: LocalDate) {
        // 1. 이전 날짜의 빈 기록 삭제
        previousSelectedDate?.let { previousDate ->     // 이전 선택 날짜가 있고, 복용 기록이 없으면 클린업.
            cleanupEmptyLog(previousDate)               // 클린업은 이전 선택 날짜가 있을 때만 실행 가능하니까!
        }

        // 현재 날짜로 업데이트
        previousSelectedDate = selectedDate
        mutableUiState.value = mutableUiState.value.copy(selectedDate = selectedDate)
        
        // 선택한 날짜의 복용 기록 로드 또는 생성
        ensureDailyLogExists(selectedDate)

        // 즐겨찾기 최신으로 갱신
        loadFavorites()
    }

    // 빈 기록 삭제 (모든 items의 taken이 false인 경우)
    private fun cleanupEmptyLog(date: LocalDate) {
        val dateString = CalendarDetailUtils.formatDate(date)
        val log = TempData.logs.find { it.date == dateString }

        if (log != null) {
            // 모든 items의 taken이 false인지 확인
            val allFalse = log.items.values.all { !it.taken }
            if (allFalse) {
                TempData.logs.remove(log)
            }
        }
    }
    
    // 선택한 날짜의 DailyLog가 존재하는지 확인하고, 없으면 생성
    private fun ensureDailyLogExists(selectedDate: LocalDate) {
        val dateString = CalendarDetailUtils.formatDate(selectedDate)
        val existingLog = TempData.logs.find { it.date == dateString }
        
        if (existingLog == null) {
            // logs에 해당 날짜가 없으면 새로 생성
            val newItems = TempData.favorites.associate { favorite ->
                favorite.itemSeq to DailyLogItem(taken = false)
            }
            val newLog = DailyLog(date = dateString, items = newItems)
            TempData.logs.add(newLog)
            TempData.sortLogs()  // 날짜 순으로 정렬
        }
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
        viewModelScope.launch { // 네트워크(API) 통신을 위해 필요
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

    // 의약품 복용 상태 로드 (TempData.logs에서 실제 데이터 읽기)
    private fun loadMedicineTakenStatus(itemSeq: String) {
        viewModelScope.launch { // 네트워크(API) 통신을 위해 필요
            val selectedDate = mutableUiState.value.selectedDate
            val dateString = CalendarDetailUtils.formatDate(selectedDate)
            val dailyLog = TempData.logs.find { it.date == dateString }
            val isTaken = dailyLog?.items?.get(itemSeq)?.taken ?: false
            
            mutableUiState.value = mutableUiState.value.copy(
                medicineTakenStatus = mutableUiState.value.medicineTakenStatus + (itemSeq to isTaken)
            )
        }
    }

    // 이벤트 처리
    fun handleEvent(event: CalendarDetailEvent) {
        when (event) {
            is CalendarDetailEvent.OnMedicineTakenChanged -> {
                updateMedicineTakenStatus(event.itemSeq, event.isTaken)
            }
            is CalendarDetailEvent.OnMedicineDetailClick -> {
                mutableUiState.value = mutableUiState.value.copy(selectedMedicineId = event.itemSeq)
            }
            is CalendarDetailEvent.OnMedicineDetailBack -> {
                mutableUiState.value = mutableUiState.value.copy(selectedMedicineId = null)
            }
        }
    }

    // 의약품 복용 상태 업데이트 (TempData.logs 실제 업데이트)
    private fun updateMedicineTakenStatus(itemSeq: String, isTaken: Boolean) {
        viewModelScope.launch { // 네트워크(API) 통신을 위해 필요
            val selectedDate = mutableUiState.value.selectedDate
            val dateString = CalendarDetailUtils.formatDate(selectedDate)
            val logIndex = TempData.logs.indexOfFirst { it.date == dateString }
            
            if (logIndex != -1) {
                // 기존 로그 업데이트
                val existingLog = TempData.logs[logIndex]                   // 기존 로그 가져오기
                val updatedItems = existingLog.items.toMutableMap()         // Map을 변경 가능하게 복사
                updatedItems[itemSeq] = DailyLogItem(taken = isTaken)       // 해당 의약품의 taken 상태 업데이트
                val updatedLog = existingLog.copy(items = updatedItems)     // 새로운 DailyLog 객체 생성
                TempData.logs[logIndex] = updatedLog                        // 리스트에 업데이트된 로그 저장
            } else {
                // 해당 날짜의 로그가 없으면 새로 생성
                // 모든 즐겨찾기 의약품을 포함하되, 클릭한 의약품만 isTaken 상태로 설정. 나머지는 taken = false
                val newItems = TempData.favorites.associate { favorite ->
                    favorite.itemSeq to DailyLogItem(taken = if (favorite.itemSeq == itemSeq) isTaken else false)
                }
                val newLog = DailyLog(date = dateString, items = newItems)
                TempData.logs.add(newLog)
                TempData.sortLogs()
            }
            
            // UI 상태도 업데이트
            mutableUiState.value = mutableUiState.value.copy(
                medicineTakenStatus = mutableUiState.value.medicineTakenStatus + (itemSeq to isTaken)
            )
        }
    }
}
