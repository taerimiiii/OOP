package com.example.oop.ui.calendar

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

// 날짜 계산/서식 helper는 object로 관리하기.
object CalendarUtils {
    // 한국 시간 기준으로 날짜 형식 변환
    fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA))
    }

    // 월간 캘린더 타이틀에서 연도랑 월만 사용해서 일 떼어 진 거 필요.
    fun formatOutYearMonth(yearMonth: YearMonth): String {
        return yearMonth.format(DateTimeFormatter.ofPattern("yyyy년 M월", Locale.KOREA))
    }

    fun formatOutYearMonthDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREA))
    }

    fun formatOutMonthDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA))
    }

    // 출석 계산할 때 쓸 지난달 이번달 년월 계산에 사용.
    fun formatYearMonth(yearMonth: YearMonth): String {
        return yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM", Locale.getDefault()))
    }

    // 현재 월의 년월 반환
    fun getCurrentMonth(): YearMonth {
        return YearMonth.now()
    }

    // 이전 월 반환
    fun getPreviousMonth(yearMonth: YearMonth): YearMonth {
        return yearMonth.minusMonths(1)
    }

    // 다음 월 반환
    fun getNextMonth(yearMonth: YearMonth): YearMonth {
        return yearMonth.plusMonths(1)
    }
}
