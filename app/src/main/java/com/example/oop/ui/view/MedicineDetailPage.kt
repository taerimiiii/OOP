package com.example.oop.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource // 이 import가 필요합니다
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Divider
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun MyBottomNavBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val greenColor = Color(0xFF71E000) // 아이콘에 사용할 녹색

    // Column으로 감싸서 구분선과 네비게이션 바를 수직으로 쌓습니다.
    Column(modifier = Modifier.background(Color.White)) { // 흰색 배경

        // 1. 얉은 회색 구분선
        Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp,
            )

        // 2. 하단 네비게이션 바
        NavigationBar(
            modifier = Modifier.height(56.dp), // 회색 구분선 높이
            containerColor = Color.White, // 흰색 배경
            tonalElevation = 0.dp // 그림자 제거
        ) {
            // 항목 1: 검색 (돋보기)
            NavigationBarItem(
                selected = (selectedItem == 0),
                onClick = { onItemClick(0) },
                icon = { Icon(Icons.Default.Search, "Search") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor, // 선택 시 녹색
                    unselectedIconColor = greenColor, // 선택 안 돼도 녹색
                    indicatorColor = Color.Transparent // 아이콘 주변 동그라미 제거
                )
            )

            // 항목 2: 홈
            NavigationBarItem(
                selected = (selectedItem == 1),
                onClick = { onItemClick(1) },
                icon = { Icon(Icons.Default.Home, "Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )

            // 항목 3: 캘린더
            NavigationBarItem(
                selected = (selectedItem == 2),
                onClick = { onItemClick(2) },
                icon = { Icon(Icons.Default.DateRange, "Calendar") }, // 👈 캘린더 아이콘
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
@Composable
fun MedicineDetailScreen() {
    // 전체 화면을 스크롤 가능하게 만들기
    var selectedItem by rememberSaveable { mutableStateOf(1) } // 1 = Home (0, 1, 2 순서)

    // 2. Scaffold로 화면 전체를 감쌉니다.
    Scaffold(
        // 3. 하단 바 슬롯에 우리가 만들 네비게이션 바를 지정
        bottomBar = {
            MyBottomNavBar(
                selectedItem = selectedItem,
                onItemClick = { selectedItem = it }
            )
        }
    )
    { contentPadding ->
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding)
            .verticalScroll(scrollState)
    ) {
        // 1. 상단 헤더와 이미지가 겹치는 영역 (Box 사용)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // 이 300dp 높이를 기준으로 이미지를 배치합니다
        ) {

            // (1) 로고와 초록 배경을 담을 '수직' 컨테이너
            Column(modifier = Modifier.fillMaxSize()) {

                // ----------------------------------------------------
                // "1번~5번 줄" (여백)
                // ----------------------------------------------------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f) // (5/20 비율)
                ) {
                    // 여백 안에 로고 배치
                    Image(
                        painter = painterResource(id = R.drawable.my_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 16.dp, top = 16.dp)
                            .size(50.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // ----------------------------------------------------
                // "6번~20번 줄" (초록색 배경)
                // ----------------------------------------------------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9f) // (15/20 비율)

                        .background(Color(0xFF71e000)) // 예: LightGreen 500
                ) {
                    // 초록 배경 안에 뒤로가기 버튼 배치
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopStart) // 초록 박스 기준 좌측 상단
                            .padding(start = 16.dp, top = 16.dp)
                            .size(30.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f) // (5/20 비율)
                )
            }

            // (2) 겹쳐진 약 이미지
            Image(
                painter = painterResource(id = R.drawable.my_logo),
                contentDescription = "Medicine Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(260.dp, 140.dp)
                    .align(Alignment.TopCenter) // 정렬 기준을 위쪽 중앙으로 변경
                    .offset(y = 140.dp) // 의약품 이미지가 위에서부터 얼마나 떨어져있는지
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray) // 임시 배경
            )

        }

        // 2. 내용 영역
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "#색상 #원형 #정제",
                    color = Color(0xFF66BB00),
                    fontSize = 16.sp
                )

                // 즐겨찾기 아이콘
                Icon(
                    imageVector = Icons.Default.Star, // 즐겨찾기 아이콘 (채워진 별/빈 별)
                    contentDescription = "Favorite",
                    tint = Color.Gray, // 활성화되면 노란색 등으로 변경
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 약 이름
            Text(text = "약품명", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "약품영문명", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = "일동제약",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 상세 정보 박스 (Card 대신 Border가 있는 Box 사용)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    InfoRow(label = "분류명", value = "소화제")
                    InfoRow(label = "가로 X 세로", value = "7.2 X 7.2mm")
                    InfoRow(label = "두께", value = "2.8mm")
                    InfoRow(label = "판매 구분", value = "전문의약품")
                    InfoRow(label = "제조사 코드", value ="2")
                    InfoRow(label = "각인", value = "A")
                }
            }
        }
      }
    }
}



@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = "$label : $value", fontSize = 16.sp, color = Color.Black)
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MedicineDetailScreen()
}

