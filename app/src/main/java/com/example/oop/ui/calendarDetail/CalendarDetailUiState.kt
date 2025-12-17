package com.example.oop.ui.calendarDetail

import com.example.oop.data.model.Favorite
import com.example.oop.data.model.Medicine
import java.time.LocalDate

// 화면 표현에 필요한 상태 묶음
data class CalendarDetailUiState(
    val selectedDate: LocalDate,
    val favorites: List<Favorite> = emptyList(),
    val medicines: Map<String, Medicine> = emptyMap(),          // itemSeq -> Medicine
    val medicineTakenStatus: Map<String, Boolean> = emptyMap(), // itemSeq -> isTaken(Screen에 있음)
    val selectedMedicineId: String? = null,                     // 선택된 의약품 ID (의약품상세 화면 표시용)
    val errorMessage: String? = null,
)
