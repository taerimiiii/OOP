//package com.example.oop.ui.view
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.Star
//import androidx.compose.material.icons.outlined.StarOutline
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.oop.R
//import com.example.oop.ui.viewmodel.MedicineDetailViewModel
//
//// 뷰로 보기
//@Composable
//fun MedicineDetailView(
//    medicineId: String = "medicine_001", // 약품 ID
//    onBackClick: () -> Unit = {}, // 뒤로가기 콜백
//    viewModel: MedicineDetailViewModel = viewModel() // ViewModel
//) {
//    // ViewModel에 약품 ID 설정
//    LaunchedEffect(medicineId) {
//        viewModel.setMedicineId(medicineId)
//    }
//
//    // ViewModel에서 즐겨찾기 상태 관찰
//    val isFavorite by viewModel.isFavorite.collectAsState()
//
//    var selectedItem by rememberSaveable { mutableIntStateOf(1) }
//
//    Scaffold(
//        bottomBar = {
//            MyBottomNavBar(
//                selectedItem = selectedItem,
//                onItemClick = { selectedItem = it }
//            )
//        }
//    ) { contentPadding ->
//        val scrollState = rememberScrollState()
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(contentPadding)
//                .verticalScroll(scrollState)
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            ) {
//                Column(modifier = Modifier.fillMaxSize()) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(5f)
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.my_logo),
//                            contentDescription = "App Logo",
//                            modifier = Modifier
//                                .align(Alignment.TopStart)
//                                .padding(start = 16.dp, top = 16.dp)
//                                .size(50.dp),
//                            contentScale = ContentScale.Fit
//                        )
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(9f)
//                            .background(Color(0xFF71e000))
//                    ) {
//                        // 뒤로가기 버튼
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White,
//                            modifier = Modifier
//                                .align(Alignment.TopStart)
//                                .padding(start = 16.dp, top = 16.dp)
//                                .size(30.dp)
//                                .clickable {
//                                    viewModel.onBackPressed()
//                                    onBackClick()
//                                }
//                        )
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(6f)
//                    )
//                }
//
//                Image(
//                    painter = painterResource(id = R.drawable.my_logo),
//                    contentDescription = "Medicine Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(260.dp, 140.dp)
//                        .align(Alignment.TopCenter)
//                        .offset(y = 140.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(Color.Gray)
//                )
//            }
//
//            Column(
//                modifier = Modifier.padding(20.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.Top
//                ) {
//                    Text(
//                        text = "#색상 #원형 #정제",
//                        color = Color(0xFF66BB00),
//                        fontSize = 16.sp
//                    )
//
//                    // 즐겨찾기 아이콘
//                    Icon(
//                        imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
//                        contentDescription = if (isFavorite) "복용중" else "복용 안함",
//                        tint = if (isFavorite) Color(0xFFFFD700) else Color.Gray,
//                        modifier = Modifier
//                            .size(40.dp)
//                            .clickable {
//                                viewModel.toggleFavorite()
//                            }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(text = "약품명", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                Text(text = "약품영문명", fontSize = 16.sp, color = Color.Gray)
//                Text(
//                    text = "일동제약",
//                    fontSize = 14.sp,
//                    color = Color.DarkGray,
//                    modifier = Modifier.padding(top = 4.dp)
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
//                        .padding(20.dp)
//                ) {
//                    Column {
//                        InfoRow(label = "분류명", value = "소화제")
//                        InfoRow(label = "가로 X 세로", value = "7.2 X 7.2mm")
//                        InfoRow(label = "두께", value = "2.8mm")
//                        InfoRow(label = "판매 구분", value = "전문의약품")
//                        InfoRow(label = "제조사 코드", value = "2")
//                        InfoRow(label = "각인", value = "A", isLast = true)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MyBottomNavBar(
//    selectedItem: Int,
//    onItemClick: (Int) -> Unit
//) {
//    val greenColor = Color(0xFF71E000)
//
//    Column(modifier = Modifier.background(Color.White)) {
//        HorizontalDivider(
//            color = Color.LightGray.copy(alpha = 0.5f),
//            thickness = 1.dp
//        )
//
//        NavigationBar(
//            modifier = Modifier.height(56.dp),
//            containerColor = Color.White,
//            tonalElevation = 0.dp
//        ) {
//            NavigationBarItem(
//                selected = (selectedItem == 0),
//                onClick = { onItemClick(0) },
//                icon = { Icon(Icons.Default.Search, "Search") },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = greenColor,
//                    unselectedIconColor = greenColor,
//                    indicatorColor = Color.Transparent
//                )
//            )
//
//            NavigationBarItem(
//                selected = (selectedItem == 1),
//                onClick = { onItemClick(1) },
//                icon = { Icon(Icons.Default.Home, "Home") },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = greenColor,
//                    unselectedIconColor = greenColor,
//                    indicatorColor = Color.Transparent
//                )
//            )
//
//            NavigationBarItem(
//                selected = (selectedItem == 2),
//                onClick = { onItemClick(2) },
//                icon = { Icon(Icons.Default.DateRange, "Calendar") },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = greenColor,
//                    unselectedIconColor = greenColor,
//                    indicatorColor = Color.Transparent
//                )
//            )
//        }
//    }
//}
//
//@Composable
//fun InfoRow(label: String, value: String, isLast: Boolean = false) {
//    Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 16.dp)) {
//        Text(text = "$label : $value", fontSize = 16.sp, color = Color.Black)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    MedicineDetailView()
//}

package com.example.oop.ui.view

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
fun MedicineDetailView(
    medicineId: String = "medicine_001",
    onBackClick: () -> Unit = {},
    viewModel: MedicineDetailViewModel = viewModel()
) {
    LaunchedEffect(medicineId) {
        viewModel.setMedicineId(medicineId)
    }

    // ViewModel 상태 관찰
    val medicine by viewModel.medicine.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = {
            MyBottomNavBar(
                selectedItem = selectedItem,
                onItemClick = { selectedItem = it }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
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

                            // 약품 이미지 (실제 데이터)
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
                                Text(
                                    text = medicine!!.chart ?: "#정보없음",
                                    color = Color(0xFF66BB00),
                                    fontSize = 16.sp
                                )

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

                            Text(
                                text = medicine!!.itemName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            medicine!!.itemEngName?.let {
                                Text(
                                    text = it,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }

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
                                    InfoRow(label = "분류명", value = medicine!!.className ?: "-")
                                    InfoRow(label = "가로 X 세로", value = "정보 없음")
                                    InfoRow(label = "두께", value = "정보 없음")
                                    InfoRow(label = "판매 구분", value = "정보 없음")
                                    InfoRow(label = "제조사 코드", value = "정보 없음")
                                    InfoRow(label = "각인", value = "정보 없음", isLast = true)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomNavBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val greenColor = Color(0xFF71E000)

    Column(modifier = Modifier.background(Color.White)) {
        HorizontalDivider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier.height(56.dp),
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = (selectedItem == 0),
                onClick = { onItemClick(0) },
                icon = { Icon(Icons.Default.Search, "Search") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )

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

            NavigationBarItem(
                selected = (selectedItem == 2),
                onClick = { onItemClick(2) },
                icon = { Icon(Icons.Default.DateRange, "Calendar") },
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
fun InfoRow(label: String, value: String, isLast: Boolean = false) {
    Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 16.dp)) {
        Text(text = "$label : $value", fontSize = 16.sp, color = Color.Black)
    }
}