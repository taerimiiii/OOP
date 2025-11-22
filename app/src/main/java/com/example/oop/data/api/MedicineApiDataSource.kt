package com.example.oop.data.api

import com.example.oop.BuildConfig
import com.example.oop.data.api.model.MedicineApiResponse
import com.example.oop.data.model.Medicine

// API 데이터 소스
class MedicineApiDataSource {
    private val apiService = MedicineApiClient.apiService

    /**
     * 의약품 목록 조회
     * @param pageNo 페이지 번호 (기본값: 1)
     * @param numOfRows 한 페이지 결과 수 (기본값: 50)
     * @param type 응답 데이터 형식 (기본값: "json")
     * @param itemName 품목명 (선택)
     * @param entpName 업체명 (선택)
     * @param itemSeq 품목일련번호 (선택)
     * @param imgRegistTs 약학정보원 이미지 생성일 (선택)
     * @param ediCode 보험코드 (선택)
     * @param brizrno 사업자등록번호 (선택)
     * @return MedicineApiResponse API 응답
     */
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
            val response = apiService.getMedicineList(
                serviceKey = BuildConfig.API_KEY,
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
                Result.failure(Exception("API Error: ${response.header.resultMsg}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Medicine 모델 리스트로 변환하여 반환
    // 이후 수정 예정.
    suspend fun getMedicines(
        pageNo: Int = 1,
        numOfRows: Int = 50,
        type: String = "json",
        itemName: String? = null,
        entpName: String? = null,
        itemSeq: String? = null,
        imgRegistTs: String? = null,
        ediCode: String? = null,
        brizrno: String? = null
    ): Result<List<Medicine>> {
        return getMedicineList(
            pageNo = pageNo,
            numOfRows = numOfRows,
            type = type,
            itemName = itemName,
            entpName = entpName,
            itemSeq = itemSeq,
            imgRegistTs = imgRegistTs,
            ediCode = ediCode,
            brizrno = brizrno
        ).map { response ->
            response.body.items.map { it.toMedicine() }
        }
    }
}

