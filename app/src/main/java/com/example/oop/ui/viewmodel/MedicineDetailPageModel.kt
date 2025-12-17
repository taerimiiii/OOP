
package com.example.oop.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Medicine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicineDetailViewModel : ViewModel() {

    private val apiDataSource = MedicineApiDataSource()

    // 약품 상세 정보
    private val _medicine = MutableStateFlow<Medicine?>(null)
    val medicine: StateFlow<Medicine?> = _medicine.asStateFlow()

    // 로딩 상태
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 에러 메시지
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // 즐겨찾기 상태
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentMedicineId: String = ""

    fun setMedicineId(medicineId: String) {
        if (medicineId == currentMedicineId || medicineId.isBlank()) {
            return
        }

        currentMedicineId = medicineId

        viewModelScope.launch {
            try {
                Log.d("MedicineDetailViewModel", "상세 조회 시작: $medicineId")

                _isLoading.value = true
                _errorMessage.value = null

                val result = apiDataSource.getMedicineList(
                    pageNo = 1,
                    numOfRows = 1,  // 1개만 가져오기
                    type = "json",
                    itemSeq = medicineId  // itemSeq를 약품명으로 검색
                )

                result.onSuccess { response ->
                    val items = response.body.items ?: emptyList()

                    if (items.isNotEmpty()) {
                        Log.d("MedicineDetailViewModel", "조회 성공: ${items[0].itemName}")
                        _medicine.value = items[0].toSearchMedicine()
                    } else {
                        _errorMessage.value = "약품 정보를 찾을 수 없습니다"
                    }
                }.onFailure { exception ->
                    Log.e("MedicineDetailViewModel", "조회 실패", exception)
                    _errorMessage.value = exception.message ?: "약품 정보를 불러올 수 없습니다"
                }

            } catch (e: Exception) {
                Log.e("MedicineDetailViewModel", "예외 발생", e)
                _errorMessage.value = "알 수 없는 오류가 발생했습니다"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    fun onBackPressed() {
        // 뒤로가기 전 처리
    }

    fun clearError() {
        _errorMessage.value = null
    }
}