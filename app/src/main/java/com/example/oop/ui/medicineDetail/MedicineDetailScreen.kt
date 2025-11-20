package com.example.oop.ui.medicineDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.oop.R
import com.example.oop.ui.viewmodel.MedicineDetailViewModel

@Composable
fun MedicineDetailScreen(
    medicineId: String = "medicine_001", // 약품 ID
    onBackClick: () -> Unit = {}, // 뒤로가기 콜백
    viewModel: MedicineDetailViewModel = viewModel() // ViewModel
) {
    // ViewModel에 약품 ID 설정
    LaunchedEffect(medicineId) {
        viewModel.setMedicineId(medicineId)
    }

    // ViewModel에서 즐겨찾기 상태 관찰
    val isFavorite by viewModel.isFavorite.collectAsState()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                    ) {
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

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(9f)
                            .background(Color(0xFF71e000))
                    ) {
                        // 뒤로가기 버튼
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 16.dp, top = 16.dp)
                                .size(30.dp)
                                .clickable {
                                    viewModel.onBackPressed()
                                    onBackClick()
                                }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.my_logo),
                    contentDescription = "Medicine Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(260.dp, 140.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                )
            }

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
                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = if (isFavorite) "복용중" else "복용 안함",
                        tint = if (isFavorite) Color(0xFFFFD700) else Color.Gray,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                viewModel.toggleFavorite()
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "약품명", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "약품영문명", fontSize = 16.sp, color = Color.Gray)
                Text(
                    text = "일동제약",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                        InfoRow(label = "제조사 코드", value = "2")
                        InfoRow(label = "각인", value = "A", isLast = true)
                    }
                }
            }
        }

}

@Composable
fun InfoRow(label: String, value: String, isLast: Boolean = false) {
    Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 16.dp)) {
        Text(text = "$label : $value", fontSize = 16.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MedicineDetailScreen()
}