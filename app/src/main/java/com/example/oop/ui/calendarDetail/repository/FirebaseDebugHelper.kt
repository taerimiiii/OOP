package com.example.oop.ui.calendarDetail.repository

import android.util.Log
import com.example.oop.data.api.FirebaseDataSource
import kotlinx.coroutines.tasks.await

/**
 * Firebase 데이터베이스 디버깅을 위한 헬퍼 클래스
 * 앱에서 직접 DB 데이터를 확인할 수 있도록 도와줍니다.
 */
object FirebaseDebugHelper {
    private val db = FirebaseDataSource.db
    private const val TAG = "FirebaseDebugHelper"

    /**
     * 특정 사용자의 모든 즐겨찾기 데이터 출력
     */
    suspend fun printAllFavorites(userId: String) {
        try {
            Log.d(TAG, "========== 즐겨찾기 데이터 조회 시작 ==========")
            Log.d(TAG, "사용자 ID: $userId")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()
            
            Log.d(TAG, "총 문서 수: ${snapshot.documents.size}")
            
            if (snapshot.documents.isEmpty()) {
                Log.d(TAG, "⚠️ 즐겨찾기 데이터가 없습니다!")
            } else {
                snapshot.documents.forEachIndexed { index, doc ->
                    Log.d(TAG, "--- 즐겨찾기 #${index + 1} ---")
                    Log.d(TAG, "문서 ID: ${doc.id}")
                    Log.d(TAG, "전체 데이터: ${doc.data}")
                    Log.d(TAG, "itemSeq: ${doc.getString("itemSeq")}")
                }
            }
            
            Log.d(TAG, "========== 즐겨찾기 데이터 조회 완료 ==========")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 즐겨찾기 데이터 조회 실패", e)
        }
    }

    /**
     * 특정 사용자의 모든 일일 로그 데이터 출력
     */
    suspend fun printAllDailyLogs(userId: String) {
        try {
            Log.d(TAG, "========== 일일 로그 데이터 조회 시작 ==========")
            Log.d(TAG, "사용자 ID: $userId")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .get()
                .await()
            
            Log.d(TAG, "총 문서 수: ${snapshot.documents.size}")
            
            if (snapshot.documents.isEmpty()) {
                Log.d(TAG, "⚠️ 일일 로그 데이터가 없습니다!")
            } else {
                snapshot.documents.forEachIndexed { index, doc ->
                    Log.d(TAG, "--- 일일 로그 #${index + 1} ---")
                    Log.d(TAG, "문서 ID: ${doc.id}")
                    Log.d(TAG, "전체 데이터: ${doc.data}")
                    Log.d(TAG, "itemSeq: ${doc.getString("itemSeq")}")
                    Log.d(TAG, "date: ${doc.getString("date")}")
                    Log.d(TAG, "isTaken: ${doc.getBoolean("isTaken")}")
                }
            }
            
            Log.d(TAG, "========== 일일 로그 데이터 조회 완료 ==========")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 일일 로그 데이터 조회 실패", e)
        }
    }

    /**
     * 특정 날짜의 모든 로그 데이터 출력
     */
    suspend fun printDailyLogsByDate(userId: String, date: String) {
        try {
            Log.d(TAG, "========== 특정 날짜 로그 데이터 조회 시작 ==========")
            Log.d(TAG, "사용자 ID: $userId")
            Log.d(TAG, "조회 날짜: $date")
            
            val snapshot = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .whereEqualTo("date", date)
                .get()
                .await()
            
            Log.d(TAG, "총 문서 수: ${snapshot.documents.size}")
            
            if (snapshot.documents.isEmpty()) {
                Log.d(TAG, "⚠️ 해당 날짜의 로그 데이터가 없습니다!")
            } else {
                snapshot.documents.forEachIndexed { index, doc ->
                    Log.d(TAG, "--- 로그 #${index + 1} ---")
                    Log.d(TAG, "문서 ID: ${doc.id}")
                    Log.d(TAG, "전체 데이터: ${doc.data}")
                }
            }
            
            Log.d(TAG, "========== 특정 날짜 로그 데이터 조회 완료 ==========")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 특정 날짜 로그 데이터 조회 실패", e)
        }
    }

    /**
     * 모든 컬렉션 구조 확인
     */
    suspend fun printCollectionStructure(userId: String) {
        try {
            Log.d(TAG, "========== 컬렉션 구조 조회 시작 ==========")
            Log.d(TAG, "사용자 ID: $userId")
            
            // users 컬렉션 확인
            val userDoc = db.collection("users")
                .document(userId)
                .get()
                .await()
            
            Log.d(TAG, "--- users/$userId 문서 ---")
            if (userDoc.exists()) {
                Log.d(TAG, "문서 존재: true")
                Log.d(TAG, "문서 데이터: ${userDoc.data}")
            } else {
                Log.d(TAG, "⚠️ 문서가 존재하지 않습니다!")
            }
            
            // 하위 컬렉션 확인
            val favoritesSnapshot = db.collection("users")
                .document(userId)
                .collection("favorites")
                .get()
                .await()
            
            Log.d(TAG, "--- favorites 하위 컬렉션 ---")
            Log.d(TAG, "문서 수: ${favoritesSnapshot.documents.size}")
            
            val dailyLogsSnapshot = db.collection("users")
                .document(userId)
                .collection("dailyLogs")
                .get()
                .await()
            
            Log.d(TAG, "--- dailyLogs 하위 컬렉션 ---")
            Log.d(TAG, "문서 수: ${dailyLogsSnapshot.documents.size}")
            
            Log.d(TAG, "========== 컬렉션 구조 조회 완료 ==========")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 컬렉션 구조 조회 실패", e)
        }
    }
}

