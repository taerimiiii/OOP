package com.example.oop.data.model

data class DailyLogItem(
    val taken: Boolean = false,
)

data class DailyLog(
    val date: String = "", // "yyyy-MM-dd"
    val items: Map<String, DailyLogItem> = emptyMap()
)