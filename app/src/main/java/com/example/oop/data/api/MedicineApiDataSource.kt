package com.example.oop.data.api

import com.example.oop.BuildConfig
import com.example.oop.data.api.model.MedicineApiResponse
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
            // API_KEY 확인
            val apiKey = BuildConfig.API_KEY
            if (apiKey.isBlank()) {
                return Result.failure(Exception("API_KEY가 설정되지 않았습니다. local.properties 파일에 API_KEY를 추가해주세요."))
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

            if (response.header.resultCode == "00") {
                Result.success(response)
            } else {
                Result.failure(Exception("API Error: ${response.header.resultMsg} (resultCode: ${response.header.resultCode})"))
            }
        } catch (e: Exception) {
            // HTTP 401 오류인 경우 더 명확한 메시지 제공
            val errorMessage = when {
                e.message?.contains("401") == true -> "인증 실패: API_KEY가 유효하지 않거나 설정되지 않았습니다. local.properties 파일에 올바른 API_KEY를 설정해주세요."
                e.message?.contains("Unauthorized") == true -> "인증 실패: API_KEY가 유효하지 않습니다. 공공데이터포털에서 발급받은 인증키를 확인해주세요."
                else -> "API 호출 실패: ${e.message}"
            }
            Result.failure(Exception(errorMessage))
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