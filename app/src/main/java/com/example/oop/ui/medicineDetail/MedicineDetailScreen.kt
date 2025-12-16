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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.oop.R
import com.example.oop.ui.viewmodel.MedicineDetailViewModel

@Composable
fun MedicineDetailScreen(
    medicineId: String = "medicine_001",
    onBackClick: () -> Unit = {},
    viewModel: MedicineDetailViewModel = viewModel()
) {
    // ViewModel에 약품 ID 설정
    LaunchedEffect(medicineId) {
        viewModel.setMedicineId(medicineId)
    }

    // ViewModel 상태 관찰
    val medicine by viewModel.medicine.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // 로딩 중
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF71E000)
                )
            }

            // 에러 발생
            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = errorMessage ?: "오류가 발생했습니다",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.clearError()
                            viewModel.setMedicineId(medicineId)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF71E000)
                        )
                    ) {
                        Text("다시 시도")
                    }
                }
            }

            // 데이터 표시
            medicine != null -> {
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

                        // 약품 이미지 - API 데이터 사용
                        Box(
                            modifier = Modifier
                                .size(260.dp, 140.dp)
                                .align(Alignment.TopCenter)
                                .offset(y = 140.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFE8F5E9)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (medicine!!.itemImage != null) {
                                AsyncImage(
                                    model = medicine!!.itemImage,
                                    contentDescription = "Medicine Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit,
                                    placeholder = painterResource(id = R.drawable.my_logo),
                                    error = painterResource(id = R.drawable.my_logo)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.my_logo),
                                    contentDescription = "Medicine Image",
                                    modifier = Modifier.size(100.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            // 성상 정보 - API 데이터 사용
                            Text(
                                text = medicine!!.chart ?: "#정보없음",
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

                        // 약품명 - API 데이터 사용
                        Text(
                            text = medicine!!.itemName!!,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // 영문명 - API 데이터 사용
                        medicine!!.itemEngName?.let {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }

                        // 제조사 - API 데이터 사용
                        Text(
                            text = medicine!!.entpName ?: "제조사 정보 없음",
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
                                // 분류명 - API 데이터 사용
                                InfoRow(label = "분류명", value = medicine!!.className ?: "-")

                                // 가로 X 세로 - API 데이터 사용
                                InfoRow(
                                    label = "가로 X 세로",
                                    value = if (medicine!!.lengLong != null || medicine!!.lengShort != null) {
                                        "${medicine!!.lengLong ?: "-"} X ${medicine!!.lengShort ?: "-"}mm"
                                    } else {
                                        "-"
                                    }
                                )

                                // 두께 - API 데이터 사용
                                InfoRow(
                                    label = "두께",
                                    value = medicine!!.thick?.let { "${it}mm" } ?: "-"
                                )

                                // 판매 구분 - API 데이터 사용
                                InfoRow(label = "판매 구분", value = medicine!!.etcOtcName ?: "-")

                                // 제형 - API 데이터 사용
                                InfoRow(label = "제형", value = medicine!!.formCodeName ?: "-")

                                // 각인 - API 데이터 사용
                                InfoRow(
                                    label = "각인",
                                    value = if (medicine!!.printFront != null || medicine!!.printBack != null) {
                                        "${medicine!!.printFront ?: "-"} / ${medicine!!.printBack ?: "-"}"
                                    } else {
                                        "-"
                                    },
                                    isLast = true
                                )
                            }
                        }
                    }
                }
            }

            // 초기 상태
            else -> {
                Text(
                    text = "약품 정보를 불러오는 중...",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, isLast: Boolean = false) {
    Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 16.dp)) {
        Row {
            Text(
                text = "$label : ",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}