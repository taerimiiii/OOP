package com.example.oop.data.model

data class DailyLog(
    val date: String,                       // "yyyy-MM-dd"
    val items: Map<String, DailyLogItem>    // emptyMap()
)

data class DailyLogItem(
    val taken: Boolean // false
)
