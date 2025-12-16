package com.example.oop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oop.ui.theme.OOPTheme // 팀 프로젝트 테마 import
import kotlinx.coroutines.delay

// 1. 앱 켜자마자 나오는 로딩
@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // 2초 대기
        onLoadingFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로딩바 색상도 팀 컬러(연두색)로 맞춤
        CircularProgressIndicator(
            color = Color(0xFFC0F56F),
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Loading...", color = Color.DarkGray)
    }
}

// 2. 메인 가기 전 로딩
@Composable
fun MainContentLoadingScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onLoadingFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 필요하면 여기에 로고 추가
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    OOPTheme { // 팀 테마로 변경
        LoadingScreen(onLoadingFinished = {})
    }
}