package com.example.oop.ui.Search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.PillTopBar

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    // 임시 코드. 여기에 제품명 검색 UI 작성.
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "검색 화면을 준비 중입니다.",
            modifier = Modifier.padding(24.dp)
        )
    }
}