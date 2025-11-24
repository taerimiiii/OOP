package com.example.oop.data

import android.content.Context
import androidx.room.*

// 1. 데이터 모델 (테이블 구조)
@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey
    val medicineId: String,
    val name: String = "약품명",
    val isFavorite: Boolean = false  // 복용중 여부
)

// 2. DAO (데이터베이스 작업)
@Dao
interface MedicineDao {
    // 즐겨찾기 상태 확인
    // sql 사용
    @Query("SELECT isFavorite FROM medicines WHERE medicineId = :id")
    suspend fun isFavorite(id: String): Boolean?

    // 즐겨찾기 상태 변경
    @Query("UPDATE medicines SET isFavorite = :isFavorite WHERE medicineId = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    // 약품 추가 (없으면 자동으로 추가됨)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicine)
}

// 3. 데이터베이스
@Database(entities = [Medicine::class], version = 1)
abstract class MedicineDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao

    companion object {
        private var instance: MedicineDatabase? = null

        fun getInstance(context: Context): MedicineDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MedicineDatabase::class.java,
                    "medicine_db"
                ).build().also { instance = it }
            }
        }
    }
}
