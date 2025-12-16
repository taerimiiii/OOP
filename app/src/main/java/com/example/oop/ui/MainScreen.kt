package com.example.oop.ui.theme // 👈 파일이 theme 폴더 안에 있어야 합니다!

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

// ✅ PillGreen, PillYellow는 같은 폴더(Color.kt)에 있어서 import 없이 바로 씁니다!

@Composable
fun PillHomeScreen() { // (이름 충돌 방지: PillHomeScreen)
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = PillGreen // 👈 이제 에러 안 남!
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { /* 검색 탭 */ },
                    icon = { Icon(Icons.Default.Search, "Search", tint = PillGreen) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* 홈 탭 */ },
                    icon = { Icon(Icons.Default.Home, "Home", tint = PillGreen) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { /* 캘린더 탭 */ },
                    icon = { Icon(Icons.Default.DateRange, "Calendar", tint = PillGreen) },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
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
            // 1. 상단 헤더
            TopHeaderSection()
            Spacer(modifier = Modifier.height(24.dp))
            // 2. 캘린더
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
            .background(PillYellow) // 👈 Color.kt에서 가져옴
            .padding(24.dp)
    ) {
        Text("pill", fontSize = 32.sp, color = PillGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        Text("안녕하세요!\n궁금하신 모든 의약품을 한 번에 검색해 보세요", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))

        var searchText by remember { mutableStateOf("") }
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Enter keyword to search", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(28.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = { Icon(Icons.Default.Search, "Search", tint = Color.Gray) }
        )
    }
}

@Composable
fun CalendarSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) { Icon(Icons.Default.ArrowBack, "Prev", tint = Color.DarkGray) }
            Text("2021년 October", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            IconButton(onClick = {}) { Icon(Icons.Default.ArrowForward, "Next", tint = Color.DarkGray) }
        }
        Spacer(modifier = Modifier.height(16.dp))

        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        Row(modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach { day ->
                Text(day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(300.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(36) { index ->
                if (index < 5) Spacer(modifier = Modifier.size(40.dp))
                else {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp)) {
                        Text("${index - 4}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }
                }
            }
        }
    }
}