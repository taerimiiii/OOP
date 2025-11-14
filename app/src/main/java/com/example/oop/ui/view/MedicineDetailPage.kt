package com.example.medicineapp.ui.view

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MedicineDetailScreen() {
    // 전체 화면을 스크롤 가능하게 만들기
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        // 1. 상단 헤더와 이미지가 겹치는 영역 (Box 사용)
        Box(
            modifier = Modifier.fillMaxWidth().height(280.dp) // 겹쳐진 영역 전체 높이
        ) {
            // (1) 초록색 배경 (헤더)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF66BB00)) // 연두색
            ) {
                // 뒤로가기 버튼 & 로고
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "pill",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // (2) 겹쳐진 약 이미지 (Box 안에서 아래쪽 중앙에 배치)
            // 실제 이미지가 없다면 아이콘으로 대체해두었습니다.
            Image(
                // painter = painterResource(id = R.drawable.medicine_image), // 나중에 실제 이미지로 교체
                imageVector = Icons.Default.Star, // 임시 이미지
                contentDescription = "Medicine Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 박스 바닥 중앙 정렬
                    .size(200.dp, 120.dp) // 너비 200, 높이 120
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray) // 이미지가 없을 때 회색 배경
            )
        }

        // 2. 내용 영역
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // 해시태그와 즐겨찾기 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "#흰색 #원형 \n#기타의 소화기관용약",
                    color = Color(0xFF66BB00),
                    fontSize = 14.sp
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
            Text(text = "가나메드정", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Ganamed Tab", fontSize = 16.sp, color = Color.Gray)
            Text(text = "일동제약", fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))

            Spacer(modifier = Modifier.height(24.dp))

            // 상세 정보 박스 (Card 대신 Border가 있는 Box 사용)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    InfoRow(label = "제형명", value = "정제")
                    InfoRow(label = "가로X세로", value = "7.2 X 7.2mm")
                    InfoRow(label = "두께", value = "2.8mm")
                    InfoRow(label = "판매 구분", value = "전문의약품")
                    InfoRow(label = "ATC코드", value = "A03FA07")
                }
            }
        }
    }
}

// 정보 한 줄을 보여주는 컴포넌트 (재사용을 위해 분리)
@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(text = "$label : $value", fontSize = 16.sp, color = Color.Black)
    }
}

// 미리보기 기능 (안드로이드 스튜디오 우측 상단 Split을 누르면 보입니다)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MedicineDetailScreen()
}

