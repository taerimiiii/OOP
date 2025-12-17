package com.example.oop.ui.searchResult

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.oop.data.model.Medicine
import com.example.oop.data.TempData
import com.example.oop.ui.keyword.DetailResult

// 의약품 데이터 클래스


@Composable
fun SearchResultScreen(
    searchKeyword: String,
    searchKeywordList: List<DetailResult>, //리스트 받는 프로퍼티
    viewModel: SearchResultViewModel = viewModel(),
    onMedicineClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val medicines by viewModel.medicines.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val favoriteList = TempData.favorites

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    var selectedMedicineId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(searchKeyword) {
        if (searchKeyword.isNotEmpty()) {
            viewModel.searchMedicines(searchKeyword)
        }
    }

    if (selectedMedicineId != null) {
        MedicineDetailView(
            medicineId = selectedMedicineId!!,
            onBackClick = { selectedMedicineId = null }
        )
        return
    }


    Scaffold(
        topBar = {
            SearchTopBar(
                keyword = searchKeyword,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            SearchBottomNavBar(
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
                                viewModel.searchMedicines(searchKeyword)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF71E000)
                            )
                        ) {
                            Text("다시 시도")
                        }
                    }
                }

                // 검색 결과 없음
                medicines.isEmpty() && !isLoading && errorMessage == null -> {
                    Text(
                        text = "'$searchKeyword'에 대한\n검색 결과가 없습니다",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }

                // 정상: 검색 결과 표시
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // 검색 결과 개수
                        item {
                            Text(
                                text = "검색 결과 ${medicines.size}개",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        // 검색 결과 리스트
                        items(medicines) { medicine ->
                            val isFavorite = favoriteList.any { it.itemSeq == medicine.itemSeq }

                            MedicineResultCard(
                                medicine = medicine,
                                isFavorite = isFavorite,
                                onCardClick = {
                                    // itemSeq를 상세 화면으로 전달
                                    selectedMedicineId = medicine.itemSeq
                                },
                                onFavoriteClick = {
                                    TempData.toggleFavorite(medicine.itemSeq)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchTopBar(
    keyword: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        // 로고
        Image(
            painter = painterResource(id = R.drawable.my_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(50.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // 검색 바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(2.dp, Color(0xFF71E000), RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = keyword,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF71E000))
                        .clickable { /* 검색 버튼 클릭 */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MedicineResultCard(
    medicine: Medicine,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 왼쪽: 정보
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // 제품명
                Text(
                    text = medicine.itemName!!,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // 영문명 (있으면 표시)
                medicine.itemEngName?.let { engName ->
                    Text(
                        text = "[$engName]",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 회사명
                InfoText(label = "회사명", value = medicine.entpName ?: "-")

                Spacer(modifier = Modifier.height(4.dp))

                // 분류
                InfoText(label = "분류", value = medicine.className ?: "-")

                Spacer(modifier = Modifier.height(4.dp))

                // 성상
                InfoText(label = "성상", value = medicine.chart ?: "-")
            }

            // 중간: 약 이미지
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                // 실제 이미지가 있으면 표시
                if (medicine.itemImage != null) {
                    AsyncImage(
                        model = medicine.itemImage,
                        contentDescription = "Medicine Image",
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.my_logo),
                        error = painterResource(id = R.drawable.my_logo)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.my_logo),
                        contentDescription = "Medicine Image",
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // 오른쪽: 즐겨찾기 버튼
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = "즐겨찾기",
                    // 노란색 vs 회색
                    tint = if (isFavorite) Color(0xFFFFD700) else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Row {
        Text(
            text = "$label : ",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun SearchBottomNavBar(
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
                icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = (selectedItem == 1),
                onClick = { onItemClick(1) },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = (selectedItem == 2),
                onClick = { onItemClick(2) },
                icon = { Icon(Icons.Default.DateRange, contentDescription = "Calendar") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = greenColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}