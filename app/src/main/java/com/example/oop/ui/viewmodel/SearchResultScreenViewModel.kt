package com.example.oop.ui.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Medicine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.String
import com.example.oop.data.repository.MedicineRepository

class SearchResultViewModel : ViewModel() {

    private val apiDataSource = MedicineApiDataSource()


    // 검색 결과 목록
    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines.asStateFlow()

    // 로딩 상태
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 에러 메시지
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var lastSearchKeyword: String? = null

    private val repository = MedicineRepository()


    fun searchMedicines(keyword: String) {
        // 빈 검색어는 무시
        if (keyword.isBlank()) {
            Log.d("SearchViewModel", "빈 검색어")
            _medicines.value = emptyList()
            lastSearchKeyword = null
            return
        }

        //이미 로딩중일경우 무시
        if (_isLoading.value) {
            Log.d("SearchViewModel", "이미 로딩 중: $keyword")
            return
        }

        //같은 검색어여도 무시
        if (keyword == lastSearchKeyword) {
            Log.d("SearchViewModel", "중복 검색 방지: $keyword")
            return
        }

        lastSearchKeyword = keyword

        viewModelScope.launch {
            try {
                Log.d("SearchViewModel", "검색 시작: $keyword")

                // 로딩 시작
                _isLoading.value = true
                _errorMessage.value = null


                val result = apiDataSource.getSearchResult(
                    pageNo = 1,
                    numOfRows = 100,  // 최대 100개
                    type = "json",
                    itemName = keyword
                )

                // 결과 처리
                result.onSuccess { medicineList ->
                    Log.d("SearchViewModel", "검색 성공: ${medicineList.size}개")

                    // 성공: 결과를 UI에 전달
                    _medicines.value = medicineList

                }.onFailure { exception ->
                    Log.e("SearchViewModel", "검색 실패: ${exception.message}")

                    // 실패: 에러 메시지 저장
                    _errorMessage.value = exception.message ?: "검색 중 오류가 발생했습니다"
                    _medicines.value = emptyList()
                }

            } catch (e: Exception) {
                Log.e("SearchViewModel", "예외 발생", e)
                _errorMessage.value = "알 수 없는 오류가 발생했습니다"
                _medicines.value = emptyList()

            } finally {
                // 로딩 종료
                _isLoading.value = false
            }
        }
    }

    fun searchMedicinesByAttributes(attributes: List<String>) {
        // 1. 로딩 시작
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // 2. 여기서 DB나 서버에 {각인, 제형, 모양, 색상}으로 검색 요청을 보냅니다.
            try {
                val print = attributes.getOrNull(0)?.ifEmpty { null }
                val formulation = attributes.getOrNull(1)?.ifEmpty { null }
                val shape = attributes.getOrNull(2)?.ifEmpty { null }
                val color = attributes.getOrNull(3)?.ifEmpty { null }

                val result = repository.getMedicinesByFilter(
                    print = print,
                    formulation = formulation,
                    shape = shape,
                    color = color
                )

                _medicines.value = result.map { it.toMedicine() } //결과 전송
            }catch (e: Exception) {
                _errorMessage.value = "오류 발생: ${e.message}"
            } finally {
                _isLoading.value = false
            }

            // 3. 결과를 받아오면 _medicines.value = 결과
            // 4. 로딩 종료
        }
    }

    fun com.example.oop.data.api.MedicineItem.toMedicine(): Medicine {
        return Medicine(
            itemSeq = this.itemSeq,
            itemName = this.itemName,
            entpName = this.entpName,
            itemEngName = this.itemEngName,
            itemImage = this.itemImage,
            className = this.className,
            chart = this.chart,
        )
    }

    fun clearError() {
        _errorMessage.value = null
        lastSearchKeyword = null  // 재검색 가능하도록
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SearchViewModel", "ViewModel 소멸됨")
    }
}