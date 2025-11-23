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
        @Query("chart") chart: String? = null,
        @Query("item_image") itemImage: String? = null,
        @Query("print_front") printFront: String? = null,
        @Query("print_back") printBack: String? = null,
        @Query("drug_shape") drugShape: String? = null,
        @Query("color_class1") colorClass1: String? = null,
        @Query("line_front") lineFront: String? = null,
        @Query("line_back") lineBack: String? = null,
        @Query("leng_long") lengLong: String? = null,
        @Query("leng_short") lengShort: String? = null,
        @Query("thick") thick: String? = null,
        @Query("class_name") className: String? = null,
        @Query("etc_otc_name") etcOtcName: String? = null,
        @Query("form_code_name") formCodeName: String? = null,
        @Query("mark_code_front_img") markCodeFrontImg: String? = null,
        @Query("mark_code_back_img") markCodeBackImg: String? = null,
        @Query("item_eng_name") itemEngName: String? = null
    ): MedicineApiResponse
}

