package com.example.oop.ui.calendar.repository

import android.util.Log
import com.example.oop.data.api.FirebaseDataSource
import com.example.oop.data.model.Favorite
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class CalendarRepository {
    private val db = FirebaseDataSource.db

    /**
     * 사용자의 즐겨찾기 의약품 목록 가져오기
     * @param userId 사용자 ID
     * @return Favorite 리스트
     */
    suspend fun getFavorites(userId: String): List<Favorite> {
        return try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()
            
            snapshot.documents.map { doc ->
                Favorite(
                    itemSeq = doc.getString("itemSeq") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * 특정 날짜의 복용 기록 가져오기
     * @param userId 사용자 ID
     * @param date 날짜
     * @return 복용한 의약품의 itemSeq 리스트
     */
    suspend fun getTakenMedicines(userId: String, date: String): List<String> {
        return try {
            Log.d("CalendarRepository", "getTakenMedicines 호출 - userId: $userId, date: $date")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .whereEqualTo("date", date)
                .whereEqualTo("isTaken", true)
                .get()
                .await()
            
            Log.d("CalendarRepository", "Firestore에서 가져온 문서 수: ${snapshot.documents.size}")
            
            val itemSeqs = snapshot.documents.mapNotNull { doc ->
                val itemSeq = doc.getString("itemSeq")
                Log.d("CalendarRepository", "문서 ID: ${doc.id}, itemSeq: $itemSeq, 전체 데이터: ${doc.data}")
                itemSeq
            }
            
            Log.d("CalendarRepository", "최종 복용한 의약품 itemSeq 리스트: $itemSeqs")
            itemSeqs
        } catch (e: Exception) {
            Log.e("CalendarRepository", "getTakenMedicines 에러 발생", e)
            emptyList()
        }
    }

    /**
     * 월간 출석 횟수 가져오기
     * @param userId 사용자 ID
     * @param yearMonth "yyyy-MM" 형식
     * @return 출석 횟수
     */
    suspend fun getMonthlyAttendance(userId: String, yearMonth: String): Int {
        return try {
            Log.d("CalendarRepository", "getMonthlyAttendance 호출 - userId: $userId, yearMonth: $yearMonth")
            
            val startDate = "$yearMonth-01"
            val endDate = "${yearMonth}-32"
            Log.d("CalendarRepository", "조회 범위: $startDate ~ $endDate")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThan("date", endDate)
                .whereEqualTo("isTaken", true)
                .get()
                .await()
            
            val count = snapshot.documents.size
            Log.d("CalendarRepository", "월간 출석 횟수: $count")
            Log.d("CalendarRepository", "조회된 문서들:")
            snapshot.documents.forEach { doc ->
                Log.d("CalendarRepository", "  - 문서 ID: ${doc.id}, 데이터: ${doc.data}")
            }
            
            count
        } catch (e: Exception) {
            Log.e("CalendarRepository", "getMonthlyAttendance 에러 발생", e)
            0
        }
    }
}

