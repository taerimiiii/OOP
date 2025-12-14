package com.example.oop.ui.calendarDetail.repository

import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Favorite
import com.example.oop.data.model.Medicine

class CalendarDetailRepository(
    private val apiDataSource: MedicineApiDataSource = MedicineApiDataSource()
) {
    // 사용자의 즐겨찾기 의약품 목록 가져오기
    // 임시 데이터 반환 (DB 완성 전까지)
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

    // 특정 날짜의 복용 상태 저장/업데이트
    // 임시로 로컬 상태만 관리 (DB 완성 전까지)
    suspend fun updateMedicineTakenStatus(
        userId: String,
        itemSeq: String,
        date: String,
        isTaken: Boolean
    ) {
        // DB 완성 전까지는 아무 작업도 하지 않음
        // 추후 DB 연동 시 구현
        // 임시 데이터는 TempData.logs에 하드코딩되어 있음
    }

    // 특정 날짜의 복용 상태 가져오기
    // 임시 데이터에서 조회 (DB 완성 전까지)
    suspend fun getMedicineTakenStatus(
        userId: String,
        itemSeq: String,
        date: String
    ): Boolean {
        // 임시 데이터에서 해당 날짜의 복용 상태 조회
        val dailyLog = TempData.logs.find { it.date == date }
        return dailyLog?.items?.get(itemSeq)?.taken ?: false
    }
}
