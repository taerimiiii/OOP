package com.example.oop.data.api

import com.example.oop.BuildConfig
import com.example.oop.data.api.MedicineApiResponse
import com.example.oop.data.model.Medicine

// API 데이터 소스
class MedicineApiDataSource {
    private val apiService = MedicineApiClient.apiService

    // 의약품 목록 조회
    // 원본 API 응답 반환
    // 각자 만들 조회 리턴에 넣고 필요한 데이터만 뽑아서 사용하기
    suspend fun getMedicineList(
        pageNo: Int = 1,
        numOfRows: Int = 100,
        type: String = "json",
        itemName: String? = null,
        entpName: String? = null,
        itemSeq: String? = null,
        chart: String? = null,
        itemImage: String? = null,
        printFront: String? = null,
        printBack: String? = null,
        drugShape: String? = null,
        colorClass1: String? = null,
        lineFront: String? = null,
        lineBack: String? = null,
        lengLong: String? = null,
        lengShort: String? = null,
        thick: String? = null,
        className: String? = null,
        etcOtcName: String? = null,
        formCodeName: String? = null,
        markCodeFrontImg: String? = null,
        markCodeBackImg: String? = null,
        itemEngName: String? = null,
    ): Result<MedicineApiResponse> {
        return try {
            val apiKey = BuildConfig.API_KEY
            if (apiKey.isBlank()) {
                return Result.failure(Exception("API_KEY가 설정되지 않았습니다."))
            }

            val response = apiService.getMedicineList(
                serviceKey = apiKey,
                pageNo = pageNo,
                numOfRows = numOfRows,
                type = type,
                itemName = itemName,
                entpName = entpName,
                itemSeq = itemSeq,
                chart = chart,
                itemImage = itemImage,
                printFront = printFront,
                printBack = printBack,
                drugShape = drugShape,
                colorClass1 = colorClass1,
                lineFront = lineFront,
                lineBack = lineBack,
                lengLong = lengLong,
                lengShort = lengShort,
                thick = thick,
                className = className,
                etcOtcName = etcOtcName,
                formCodeName = formCodeName,
                markCodeFrontImg = markCodeFrontImg,
                markCodeBackImg = markCodeBackImg,
                itemEngName = itemEngName,
            )

            // API 문서에 따르면, resultCode가 00일 경우 성공
            if (response.header.resultCode == "00") {
                Result.success(response)
            } else {
                Result.failure(Exception("API 오류: ${response.header.resultMsg}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("API 호출 실패: ${e.message}"))
        }
    }

    // 즐겨찾기한 의약품 조회
    suspend fun getTakeMedicines(
        pageNo: Int = 1,
        numOfRows: Int = 100,
        type: String = "json",
        itemSeq: String? = null
    ): Result<List<Medicine>> {
        return getMedicineList(
            pageNo = pageNo,
            numOfRows = numOfRows,
            type = type,
            itemSeq = itemSeq
        ).map { response ->
            response.body.items.map { it.tofavoriteMedicine() }
        }
    }

    // 이 밑으로 각자 조회 API 작성해서 사용!!

}