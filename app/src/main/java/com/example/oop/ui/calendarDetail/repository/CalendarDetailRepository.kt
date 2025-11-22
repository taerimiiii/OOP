package com.example.oop.ui.calendarDetail.repository

import android.util.Log
import com.example.oop.data.api.FirebaseDataSource
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Favorite
import com.example.oop.data.model.Medicine
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class CalendarDetailRepository(
    private val apiDataSource: MedicineApiDataSource = MedicineApiDataSource()
) {
    private val db = FirebaseDataSource.db

    /**
     * 사용자의 즐겨찾기 의약품 목록 가져오기
     * @param userId 사용자 ID
     * @return Favorite 리스트
     */
    suspend fun getFavorites(userId: String): List<Favorite> {
        return try {
            Log.d("CalendarDetailRepository", "getFavorites 호출 - userId: $userId")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()
            
            Log.d("CalendarDetailRepository", "Firestore에서 가져온 문서 수: ${snapshot.documents.size}")
            
            val favorites = snapshot.documents.map { doc ->
                val itemSeq = doc.getString("itemSeq") ?: ""
                Log.d("CalendarDetailRepository", "Favorite 문서 ID: ${doc.id}, itemSeq: $itemSeq")
                Log.d("CalendarDetailRepository", "전체 문서 데이터: ${doc.data}")
                Favorite(itemSeq = itemSeq)
            }
            
            Log.d("CalendarDetailRepository", "최종 Favorite 리스트: $favorites")
            favorites
        } catch (e: Exception) {
            Log.e("CalendarDetailRepository", "getFavorites 에러 발생", e)
            Log.d("CalendarDetailRepository", "임시 데이터 반환")
            // 에러 발생 시 임시 데이터 반환 (임의 값 넣어 둠)
            listOf(
                Favorite(itemSeq = "200808876"),
                Favorite(itemSeq = "200808877")
            )
        }
    }

    /**
     * 의약품 정보 가져오기 (API 호출)
     * @param itemSeq 품목일련번호
     * @return Medicine 객체
     */
    suspend fun getMedicine(itemSeq: String): Result<Medicine> {
        return apiDataSource.getMedicines(
            pageNo = 1,
            numOfRows = 200,
            type = "json",
            itemSeq = itemSeq
        ).map { medicines ->
            medicines.firstOrNull() ?: throw Exception("의약품 정보를 찾을 수 없습니다")
        }
    }

    /**
     * 특정 날짜의 복용 상태 저장/업데이트
     * @param userId 사용자 ID
     * @param itemSeq 품목일련번호
     * @param date 날짜 (yyyy-MM-dd)
     * @param isTaken 복용 여부
     */
    suspend fun updateMedicineTakenStatus(
        userId: String,
        itemSeq: String,
        date: String,
        isTaken: Boolean
    ) {
        try {
            val documentId = "${date}_${itemSeq}"
            val data = mapOf(
                "itemSeq" to itemSeq,
                "date" to date,
                "isTaken" to isTaken
            )
            
            Log.d("CalendarDetailRepository", "updateMedicineTakenStatus 호출")
            Log.d("CalendarDetailRepository", "userId: $userId, itemSeq: $itemSeq, date: $date, isTaken: $isTaken")
            Log.d("CalendarDetailRepository", "저장할 문서 ID: $documentId")
            Log.d("CalendarDetailRepository", "저장할 데이터: $data")
            
            db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .document(documentId)
                .set(data)
                .await()
            
            Log.d("CalendarDetailRepository", "데이터 저장 완료")
        } catch (e: Exception) {
            Log.e("CalendarDetailRepository", "updateMedicineTakenStatus 에러 발생", e)
            // 에러 처리
        }
    }

    /**
     * 특정 날짜의 복용 상태 가져오기
     * @param userId 사용자 ID
     * @param itemSeq 품목일련번호
     * @param date 날짜 (yyyy-MM-dd)
     * @return 복용 여부
     */
    suspend fun getMedicineTakenStatus(
        userId: String,
        itemSeq: String,
        date: String
    ): Boolean {
        return try {
            val documentId = "${date}_${itemSeq}"
            Log.d("CalendarDetailRepository", "getMedicineTakenStatus 호출 - userId: $userId, itemSeq: $itemSeq, date: $date")
            Log.d("CalendarDetailRepository", "조회할 문서 ID: $documentId")
            
            val doc = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .document(documentId)
                .get()
                .await()
            
            if (doc.exists()) {
                val isTaken = doc.getBoolean("isTaken") ?: false
                Log.d("CalendarDetailRepository", "문서 존재 - 전체 데이터: ${doc.data}, isTaken: $isTaken")
                isTaken
            } else {
                Log.d("CalendarDetailRepository", "문서가 존재하지 않음 - documentId: $documentId")
                false
            }
        } catch (e: Exception) {
            Log.e("CalendarDetailRepository", "getMedicineTakenStatus 에러 발생", e)
            false
        }
    }
}

