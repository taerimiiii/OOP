package com.example.oop.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.PillTopBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // 임시 코드. 여기에 홈 UI 작성.
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Welcome to MediLog")
        Text(text = "홈 콘텐츠를 여기에 구성하세요.")
    }
}