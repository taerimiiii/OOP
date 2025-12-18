package com.example.oop.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop.data.TempData
import com.example.oop.data.model.Medicine
import com.example.oop.data.repository.CalendarDetailRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CalendarDetailRepository = CalendarDetailRepository()
) : ViewModel() {
    
    private val mutableUiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = mutableUiState
    
    private val uid = TempData.user.uid
    
    init {
        loadFavorites()
    }
    
    // 즐겨찾기 의약품 목록 로드
    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                val favorites = repository.getFavorites(uid)
                mutableUiState.value = mutableUiState.value.copy(favorites = favorites)
                
                // 각 의약품 정보 로드
                for (favorite in favorites) {
                    loadMedicine(favorite.itemSeq)
                }
            } catch (e: Exception) {
                mutableUiState.value = mutableUiState.value.copy(errorMessage = e.message)
            }
        }
    }
    
    // 의약품 정보 로드
    private fun loadMedicine(itemSeq: String) {
        viewModelScope.launch {
            val result = repository.getMedicine(itemSeq)
            
            if (result.isSuccess) {
                val medicine = result.getOrNull()
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
}

data class HomeUiState(
    val favorites: List<com.example.oop.data.model.Favorite> = emptyList(),
    val medicines: Map<String, Medicine> = emptyMap(),
    val errorMessage: String? = null
)

