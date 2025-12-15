package com.example.oop.data.repository

import com.example.oop.data.api.FirebaseDataSource
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.api.MedicineItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MedicineRepository(
    private val apiDataSource: MedicineApiDataSource = MedicineApiDataSource(),
    private val firestore: FirebaseFirestore = FirebaseDataSource.db
) {
    // Firestore 컬렉션 이름 정의
    private val MEDICINE_COLLECTION = "medicines"


    suspend fun fetchAndSaveMedicines(query: String) {
        try {
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


    private suspend fun saveBulkToFirestore(items: List<MedicineItem>) {
        val batch = firestore.batch()

        items.forEach { medicine ->
            val docRef = firestore.collection(MEDICINE_COLLECTION).document(medicine.itemSeq)

            batch.set(docRef, medicine)
        }

        batch.commit().await()
        println(" ${items.size}개의 의약품 데이터가 Firestore에 성공적으로 저장되었습니다.")
    }

    suspend fun searchMedicines(query: String): Result<List<MedicineItem>> {
        if (query.isBlank()) return Result.success(emptyList())

        try {
            // 1. Firestore에서 검색을 시도합니다. (빠른 검색)
            val dbResults = searchFromDatabase(query)

            if (dbResults.isNotEmpty()) {
                println("Repository: Firestore에서 ${dbResults.size}개 결과 반환 (빠른 응답)")
                return Result.success(dbResults) // DB에 결과가 있으면 즉시 반환
            }

            println("Repository: Firestore 검색 결과 없음. API 호출 시작.")

            // 2. DB에 결과가 없거나 검색어가 새로운 경우, API에서 데이터를 가져옵니다.
            val apiResult = apiDataSource.getMedicineList(itemName = query)

            return apiResult.map { response ->
                val newMedicines = response.body.items // List<MedicineItem>

                if (newMedicines.isNotEmpty()) {
                    // 3. API에서 가져온 데이터를 Firestore에 저장합니다. (다음 검색을 위해)
                    saveBulkToFirestore(newMedicines)
                }
                println("Repository: API에서 ${newMedicines.size}개 결과 반환 및 저장 완료")
                return@map newMedicines
            }

        } catch (e: Exception) {
            println("통합 검색 중 치명적인 오류 발생: ${e.message}")
            return Result.failure(e)
        }
    }
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