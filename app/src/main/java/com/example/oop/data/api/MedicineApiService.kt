package com.example.oop.data.api

import com.example.oop.data.api.model.MedicineApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit API 인터페이스
// 모든 파라미터에 대응하는 API 호출 메서드
interface MedicineApiService {
    @GET("1471000/MdcinGrnIdntfcInfoService03/getMdcinGrnIdntfcInfoList03")
    suspend fun getMedicineList(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int? = null,
        @Query("numOfRows") numOfRows: Int? = null,
        @Query("type") type: String? = null,
        @Query("item_name") itemName: String? = null,
        @Query("entp_name") entpName: String? = null,
        @Query("item_seq") itemSeq: String? = null,
        @Query("img_regist_ts") imgRegistTs: String? = null,
        @Query("edi_code") ediCode: String? = null,
        @Query("brizrno") brizrno: String? = null
    ): MedicineApiResponse
}

