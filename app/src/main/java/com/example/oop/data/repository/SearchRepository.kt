package com.example.oop.data.repository

import com.example.oop.data.api.FirebaseDataSource
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.api.model.MedicineItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MedicineRepository(
    private val apiDataSource: MedicineApiDataSource = MedicineApiDataSource(),
    private val firestore: FirebaseFirestore = FirebaseDataSource.db
) {
    // Firestore 컬렉션 이름 정의
    private val MEDICINE_COLLECTION = "medicines"

    /**
     * [API 호출 및 저장] 외부 API 데이터를 가져와 Firestore에 저장합니다.
     */
    suspend fun fetchAndSaveMedicines(query: String) {
        try {
            // 1. API 호출: API DataSource 사용
            val result = apiDataSource.getMedicineList(itemName = query)

            result.onSuccess { response ->
                val medicineList = response.body.items
                if (medicineList.isNotEmpty()) {
                    saveBulkToFirestore(medicineList)
                } else {
                    println("API에서 가져온 데이터가 없습니다.")
                }
            }.onFailure { exception ->
                println("API 호출 실패: ${exception.message}")
                throw exception
            }

        } catch (e: Exception) {
            println("API 호출 및 저장 중 오류 발생: ${e.message}")
            throw e
        }
    }

    /**
     * [DB 저장] MedicineItem 리스트를 Firestore에 일괄 저장합니다.
     */
    private suspend fun saveBulkToFirestore(items: List<MedicineItem>) {
        val batch = firestore.batch()

        items.forEach { medicine ->
            val docRef = firestore.collection(MEDICINE_COLLECTION).document(medicine.itemSeq)

            // 💡 Firestore에 저장할 때, API 모델(MedicineItem)을 사용합니다.
            batch.set(docRef, medicine)
        }

        batch.commit().await()
        println("✅ ${items.size}개의 의약품 데이터가 Firestore에 성공적으로 저장되었습니다.")
    }

    /**
     * [DB 검색] Firestore에 저장된 데이터를 검색합니다.
     */
    suspend fun searchFromDatabase(query: String): List<MedicineItem> {
        val normalizedQuery = query.trim().lowercase()

        return try {
            val snapshot = firestore.collection(MEDICINE_COLLECTION)
                .whereGreaterThanOrEqualTo("itemName", normalizedQuery)
                .whereLessThanOrEqualTo("itemName", normalizedQuery + '\uf8ff')
                .limit(50)
                .get().await()

            // Firestore 문서를 API 모델 객체로 변환
            snapshot.toObjects(MedicineItem::class.java)
        } catch (e: Exception) {
            println("Firestore 검색 오류: ${e.message}")
            emptyList()
        }
    }
}