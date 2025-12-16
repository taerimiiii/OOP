package com.example.oop.data.model

import androidx.compose.runtime.mutableStateListOf
data class Favorite(
    val itemSeq: String = "",
)


object FavoriteStorage {
    val favoriteList = mutableStateListOf<String>()

    // 즐겨찾기 추가/삭제 함수
    fun toggleFavorite(itemSeq: String) {
        if (favoriteList.contains(itemSeq)) {
            favoriteList.remove(itemSeq) // 이미 있으면 삭제
        } else {
            favoriteList.add(itemSeq)    // 없으면 추가
        }
    }
}