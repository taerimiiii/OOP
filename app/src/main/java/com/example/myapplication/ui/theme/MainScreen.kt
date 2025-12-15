package com.example.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- 색상 정의 ---
val PillYellow = Color(0xFFF2FCCD) // 상단 배경 연한 노랑

@Composable
fun MainScreen() {
    Scaffold(
        // 하단 네비게이션 바
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = PillGreen
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { /* 검색 탭 클릭 */ },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = PillGreen) }
                )
                NavigationBarItem(
                    selected = true, // 홈이 선택된 상태
                    onClick = { /* 홈 탭 클릭 */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = PillGreen) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* 캘린더 탭 클릭 */ },
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = PillGreen) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // 1. 상단 노란색 영역 (헤더 + 검색창)
            TopHeaderSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 2. 캘린더 영역
            CalendarSection()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PillYellow) // 노란색 배경
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        // 로고 (pill)
        Text(
            text = "pill",
            fontSize = 32.sp,
            color = PillGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 인사말
        Text(
            text = "안녕하세요!\n궁금하신 모든 의약품을 한 번에 검색해 보세요",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 검색창
        var searchText by remember { mutableStateOf("") }
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Enter keyword to search", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp)), // 둥근 모서리
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White, // 검색창 배경 흰색
                focusedIndicatorColor = Color.Transparent, // 밑줄 제거
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            }
        )
    }
}

@Composable
fun CalendarSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        // 달력 헤더 (화살표와 년월)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* 이전 달 */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Prev", tint = Color.DarkGray)
            }
            Text(
                text = "2021년 October",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            IconButton(onClick = { /* 다음 달 */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 요일 표시 (Sun ~ Sat)
        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 날짜 그리드 (이미지와 똑같이 10월 1일이 금요일에 시작하도록 배치)
        // 빈 칸 5개(일~목) + 날짜 1~31
        val emptyDays = 5 // 2021년 10월 1일은 금요일이므로 앞에 5칸 공백
        val daysInMonth = 31
        val totalCells = emptyDays + daysInMonth

        LazyVerticalGrid(
            columns = GridCells.Fixed(7), // 7열
            modifier = Modifier.height(300.dp), // 달력 높이 고정
            verticalArrangement = Arrangement.spacedBy(16.dp) // 줄 간격
        ) {
            items(totalCells) { index ->
                if (index < emptyDays) {
                    // 빈 칸
                    Spacer(modifier = Modifier.size(40.dp))
                } else {
                    // 날짜 숫자
                    val day = index - emptyDays + 1
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text(
                            text = day.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}