package com.example.oop.ui.calendarDetail.repository

import com.example.oop.data.model.DailyLog
import com.example.oop.data.model.DailyLogItem
import com.example.oop.data.model.Favorite
import com.example.oop.data.model.User

// 임시 데이터
object TempData {
    // 임시 유저 데이터
    val user = User(
        uid = "1",
        email = "test@example.com",
        userName = "테스터1"
    )

    // 즐겨찾기 의약품 목록
    val favorites = listOf(
        Favorite(itemSeq = "200808876"),
        Favorite(itemSeq = "200808877"),
        Favorite(itemSeq = "200808948"),
        Favorite(itemSeq = "200809076")
    )

    // 복용 기록 데이터 (날짜 순으로 오름차순 정렬 유지)
    val logs = mutableListOf(
        DailyLog(
            date = "2025-10-20",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
                "200809076" to DailyLogItem(taken = false)
            )
        ),
        DailyLog(
            date = "2025-10-21",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = true),
                "200808948" to DailyLogItem(taken = false),
                "200809076" to DailyLogItem(taken = false)
            )
        ),
        DailyLog(
            date = "2025-10-22",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = true),
                "200808948" to DailyLogItem(taken = true),
                "200809076" to DailyLogItem(taken = false)
            )
        ),
        DailyLog(
            date = "2025-11-20",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
                "200809076" to DailyLogItem(taken = false)
            )
        ),
        DailyLog(
            date = "2025-11-21",
            items = mapOf(
                "200808876" to DailyLogItem(taken = true),
                "200808877" to DailyLogItem(taken = false),
                "200808948" to DailyLogItem(taken = false),
                "200809076" to DailyLogItem(taken = false)
            )
        )
    )

    // logs를 날짜 순으로 오름차순 정렬
    fun sortLogs() {
        logs.sortBy { it.date }
    }
}

