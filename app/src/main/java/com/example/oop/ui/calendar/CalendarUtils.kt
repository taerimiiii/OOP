package com.example.oop.ui.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

// 날짜 계산/서식 helper는 object로 관리하기.
object CalendarUtils {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
    private val monthFormatter = DateTimeFormatter.ofPattern("yyyy년 M월", Locale.getDefault())
    private val displayDateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
    private val shortDateFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.getDefault())

    /**
     * LocalDate를 "yyyy-MM-dd" 형식 문자열로 변환
     */
    fun formatDate(date: LocalDate): String {
        return date.format(dateFormatter)
    }

    /**
     * YearMonth를 "yyyy년 M월" 형식 문자열로 변환
     */
    fun formatMonth(yearMonth: YearMonth): String {
        return yearMonth.format(monthFormatter)
    }

    /**
     * LocalDate를 "yyyy년 M월 d일" 형식 문자열로 변환
     */
    fun formatDisplayDate(date: LocalDate): String {
        return date.format(displayDateFormatter)
    }

    /**
     * LocalDate를 "M월 d일" 형식 문자열로 변환
     */
    fun formatShortDate(date: LocalDate): String {
        return date.format(shortDateFormatter)
    }

    /**
     * YearMonth를 "yyyy-MM" 형식 문자열로 변환
     */
    fun formatYearMonth(yearMonth: YearMonth): String {
        return yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM", Locale.getDefault()))
    }

    /**
     * 현재 월의 YearMonth 반환
     */
    fun getCurrentMonth(): YearMonth {
        return YearMonth.now()
    }

    /**
     * 이전 월 반환
     */
    fun getPreviousMonth(yearMonth: YearMonth): YearMonth {
        return yearMonth.minusMonths(1)
    }

    /**
     * 다음 월 반환
     */
    fun getNextMonth(yearMonth: YearMonth): YearMonth {
        return yearMonth.plusMonths(1)
    }
}
