package com.example.oop.data.repository

import com.example.oop.data.TempData
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Favorite
import com.example.oop.data.model.Medicine

class CalendarDetailRepository(
    private val apiDataSource: MedicineApiDataSource = MedicineApiDataSource()
) {
    // 사용자의 즐겨찾기 의약품 목록 가져오기
    // 임시 데이터 반환
    suspend fun getFavorites(userId: String): List<Favorite> {
        // 임시 데이터 반환
        return TempData.favorites
    }

    // 의약품 정보 가져오기 (API 호출)
    // @param itemSeq 품목일련번호
    // @return Medicine 객체
    suspend fun getMedicine(itemSeq: String): Result<Medicine> {
        return apiDataSource.getTakeMedicines(
            pageNo = 1,
            numOfRows = 200,
            type = "json",
            itemSeq = itemSeq
        ).map { medicines ->
            medicines.firstOrNull() ?: throw Exception("에러발생. 의약품 정보를 찾을 수 없습니다")
        }
    }
}