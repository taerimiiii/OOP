package com.example.oop.data.model

import com.google.firebase.Timestamp

data class Medicine(
    val itemSeq: String = "",
    val itemName: String? = "",
    val entpName: String? = null,
    val itemEngName: String? = null,
    val etcOtcName: String? = null,
    val className: String? = null,
    val drugShape: String? = null,
    val color1: String? = null,
    val printFront: String? = null,
    val printBack: String? = null,
    val chart: String? = null,
    val itemImage: String? = null,
    val ediCode: String? = null,
    val bizrno: String? = null,
    val stdCd: String? = null,
    val imgRegistTs: String? = null,
)