package com.example.oop.data.api

import com.example.oop.BuildConfig
import com.example.oop.data.api.model.MedicineApiResponse
import com.example.oop.data.model.Medicine

// API 데이터 소스
class MedicineApiDataSource {
    private val apiService = MedicineApiClient.apiService

    // 의약품 목록 조회
    // 원본 API 응답 반환
    suspend fun getMedicineList(
        pageNo: Int = 1,
        numOfRows: Int = 50,
        type: String = "json",
        itemName: String? = null,
        entpName: String? = null,
        itemSeq: String? = null,
        imgRegistTs: String? = null,
        ediCode: String? = null,
        brizrno: String? = null
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
                imgRegistTs = imgRegistTs,
                ediCode = ediCode,
                brizrno = brizrno
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
        numOfRows: Int = 50,
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

