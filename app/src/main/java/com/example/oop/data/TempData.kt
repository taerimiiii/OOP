package com.example.oop.data

import com.example.oop.data.model.DailyLog
import com.example.oop.data.model.DailyLogItem
import com.example.oop.data.model.Favorite
import com.example.oop.data.model.User
import androidx.compose.runtime.mutableStateListOf

// 임시 데이터
object TempData {
    // 임시 유저 데이터
    val user = User(
        uid = "1",
        email = "test@example.com",
        userName = "테스터1"
    )

    // 즐겨찾기 의약품 목록
    val favorites = mutableStateListOf(
        Favorite(itemSeq = "200808876"),
        Favorite(itemSeq = "200808877"),
        Favorite(itemSeq = "200808948"),
    )

    fun toggleFavorite(targetItemSeq: String) {
        val existing = favorites.find { it.itemSeq == targetItemSeq }
        if (existing != null) {
            // 있으면 삭제
            favorites.remove(existing)
        } else {
            //없으면 추가
            favorites.add(Favorite(itemSeq = targetItemSeq))
        }
    }

    // 복용 기록 데이터 (날짜 순으로 오름차순 정렬 유지)
    val logs = mutableListOf(
        DailyLog(
            date = "2025-10-20",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
            )
        ),
        DailyLog(
            date = "2025-10-21",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = true),
                "200808948" to DailyLogItem(taken = false),
            )
        ),
        DailyLog(
            date = "2025-10-22",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = true),
                "200808948" to DailyLogItem(taken = true),
            )
        ),
        DailyLog(
            date = "2025-11-20",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
            )
        ),
        DailyLog(
            date = "2025-11-21",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
            )
        ),
        DailyLog(
            date = "2025-12-01",
            items = mapOf(
                "200808876" to DailyLogItem(taken = false),
                "200808877" to DailyLogItem(taken = true),
                "200808948" to DailyLogItem(taken = false),
            )
        ),
        DailyLog(
            date = "2025-12-02",
            items = mapOf(
                "200808876" to DailyLogItem(taken = false),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = true),
            )
        ),
    )

    // logs를 날짜 순으로 오름차순 정렬
    fun sortLogs() {
        logs.sortBy { it.date }
    }
}