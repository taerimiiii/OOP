package com.example.oop.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Retrofit 클라이언트 설정 (=fetch)
// Base URL, OkHttp (=axios) 로깅 인터셉터 설정
// object : 인스턴스가 하나만 생성, 전역 접근, 지연 초기화, 데이터 없음, 메모리 효율적.
object MedicineApiClient {
    private const val BASE_URL = "https://apis.data.go.kr/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 응답 지연 대응 로직(30초)
    // 공공데이터 포털 API라 응답 느린 편 + 노트북 스펙 딸려서 힘들어함 이슈로 응답 시간을 늘리고자 사용.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Gson 변환기 설정(JSON ↔ 객체 변환)
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: MedicineApiService = retrofit.create(MedicineApiService::class.java)
}