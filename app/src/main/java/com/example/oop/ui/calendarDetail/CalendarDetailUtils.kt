package com.example.oop.ui.calendarDetail

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object CalendarDetailUtils {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

    /**
     * LocalDate를 "yyyy-MM-dd" 형식 문자열로 변환
     */
    fun formatDate(date: LocalDate): String {
        return date.format(dateFormatter)
    }

    /**
     * 날짜 문자열을 LocalDate로 변환
     * @param dateString "yyyy-MM-dd" 형식
     */
    fun parseDate(dateString: String): LocalDate? {
        return try {
            LocalDate.parse(dateString, dateFormatter)
        } catch (e: Exception) {
            null
        }
    }
}
