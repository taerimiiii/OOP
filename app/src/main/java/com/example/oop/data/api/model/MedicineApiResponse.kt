package com.example.oop.data.api.model

import com.google.gson.annotations.SerializedName

// API 응답 전체 구조
data class MedicineApiResponse(
    @SerializedName("header") val header: ResponseHeader,
    @SerializedName("body") val body: ResponseBody
)

// 응답 헤더
data class ResponseHeader(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("resultMsg") val resultMsg: String
)

// 응답 바디
data class ResponseBody(
    @SerializedName("pageNo") val pageNo: Int,
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("numOfRows") val numOfRows: Int,
    @SerializedName("items") val items: List<MedicineItem>
)

// 의약품 아이템 (API 응답 형식)
data class MedicineItem(
    @SerializedName("ITEM_SEQ") val itemSeq: String,
    @SerializedName("ITEM_NAME") val itemName: String,
    @SerializedName("ENTP_SEQ") val entpSeq: String? = null,
    @SerializedName("ENTP_NAME") val entpName: String? = null,
    @SerializedName("CHART") val chart: String? = null,
    @SerializedName("ITEM_IMAGE") val itemImage: String? = null,
    @SerializedName("PRINT_FRONT") val printFront: String? = null,
    @SerializedName("PRINT_BACK") val printBack: String? = null,
    @SerializedName("DRUG_SHAPE") val drugShape: String? = null,
    @SerializedName("COLOR_CLASS1") val colorClass1: String? = null,
    @SerializedName("COLOR_CLASS2") val colorClass2: String? = null,
    @SerializedName("LINE_FRONT") val lineFront: String? = null,
    @SerializedName("LINE_BACK") val lineBack: String? = null,
    @SerializedName("LENG_LONG") val lengLong: String? = null,
    @SerializedName("LENG_SHORT") val lengShort: String? = null,
    @SerializedName("THICK") val thick: String? = null,
    @SerializedName("IMG_REGIST_TS") val imgRegistTs: String? = null,
    @SerializedName("CLASS_NO") val classNo: String? = null,
    @SerializedName("CLASS_NAME") val className: String? = null,
    @SerializedName("ETC_OTC_NAME") val etcOtcName: String? = null,
    @SerializedName("ITEM_PERMIT_DATE") val itemPermitDate: String? = null,
    @SerializedName("FORM_CODE_NAME") val formCodeName: String? = null,
    @SerializedName("MARK_CODE_FRONT_ANAL") val markCodeFrontAnal: String? = null,
    @SerializedName("MARK_CODE_BACK_ANAL") val markCodeBackAnal: String? = null,
    @SerializedName("MARK_CODE_FRONT_IMG") val markCodeFrontImg: String? = null,
    @SerializedName("MARK_CODE_BACK_IMG") val markCodeBackImg: String? = null,
    @SerializedName("ITEM_ENG_NAME") val itemEngName: String? = null,
    @SerializedName("CHANGE_DATE") val changeDate: String? = null,
    @SerializedName("MARK_CODE_FRONT") val markCodeFront: String? = null,
    @SerializedName("MARK_CODE_BACK") val markCodeBack: String? = null,
    @SerializedName("EDI_CODE") val ediCode: String? = null,
    @SerializedName("BIZRNO") val bizrno: String? = null,
    @SerializedName("STD_CD") val stdCd: String? = null
) {

    // 태림양꺼 응답 모델
    // 즐겨찾기한 의약품 조회
    fun tofavoriteMedicine(): com.example.oop.data.model.Medicine {
        return com.example.oop.data.model.Medicine(
            itemSeq = itemSeq,
            itemName = itemName,
            entpName = entpName,
            className = className,
            chart = chart,
            itemImage = itemImage,
        )
    }
}

