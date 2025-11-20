package com.example.oop.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MedicineDetailViewModel : ViewModel() {

    // 즐겨찾기 상태 (UI에서 관찰)
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    // 약품 ID (나중에 DB 연동 시 사용)
    private var currentMedicineId: String = ""

    /**
     * 약품 ID 설정
     * 나중에 DB에서 해당 약품의 즐겨찾기 상태를 불러올 예정
     */
    fun setMedicineId(medicineId: String) {
        currentMedicineId = medicineId
        // TODO: DB 연동 시 여기서 즐겨찾기 상태 불러오기
        // loadFavoriteStatusFromDB(medicineId)
    }

    /**
     * 즐겨찾기 토글 (별 버튼 클릭 시)
     * 나중에 DB에 저장하는 로직 추가 예정
     */
    fun toggleFavorite() {
        val newStatus = !_isFavorite.value
        _isFavorite.value = newStatus

        // TODO: DB 연동 시 여기서 DB에 저장
        // saveFavoriteStatusToDB(currentMedicineId, newStatus)
    }

    /**
     * 뒤로가기 버튼 처리
     * 현재는 단순히 Navigation만 처리하지만
     * 나중에 변경사항 저장 등의 로직 추가 가능
     */
    fun onBackPressed() {
        // TODO: 필요시 뒤로가기 전 처리할 작업
        // 예: 변경사항 자동 저장 등
    }

    // ========== DB 연동 시 추가될 함수들 ==========

    /**
     * DB에서 즐겨찾기 상태 불러오기 (추후 구현)
     */
    private fun loadFavoriteStatusFromDB(medicineId: String) {
        // TODO: Repository를 통해 DB에서 데이터 가져오기
        // viewModelScope.launch {
        //     val status = repository.isFavorite(medicineId)
        //     _isFavorite.value = status
        // }
    }

    /**
     * DB에 즐겨찾기 상태 저장 (추후 구현)
     */
    private fun saveFavoriteStatusToDB(medicineId: String, isFavorite: Boolean) {
        // TODO: Repository를 통해 DB에 저장
        // viewModelScope.launch {
        //     repository.updateFavoriteStatus(medicineId, isFavorite)
        // }
    }
}