package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

// 1. 로그인 버튼 누르면 나오는 첫 번째 로딩 화면
@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // 2초 대기
        onLoadingFinished() // 다음 화면으로 이동
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = Color(0xFFC0F56F),
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Loading", color = Color.DarkGray)
    }
}

// 2. 메인 화면 들어가기 직전에 나오는 로고 로딩 화면 (MainScreen에서 필요함)
@Composable
fun MainContentLoadingScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500) // 1.5초 대기
        onLoadingFinished() // 메인 화면으로 이동
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 로고 이미지 (임시로 안드로이드 기본 아이콘 사용)
        // 나중에 R.drawable.pill_logo 같은 실제 이미지로 바꾸세요
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp),
            colorFilter = ColorFilter.tint(Color(0xFF8BC34A)) // 초록색 틴트
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    MyApplicationTheme {
        LoadingScreen(onLoadingFinished = {})
    }
}