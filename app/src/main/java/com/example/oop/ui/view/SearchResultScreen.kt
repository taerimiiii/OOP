package com.example.oop.ui.view

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
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R

// 의약품 데이터 클래스
data class MedicineItem(
    val id: String,
    val name: String,
    val nameEng: String,
    val className: String,
    val shape: String,
    val chart: String,
    var isFavorite: Boolean = false
)

@Composable
fun SearchResultScreen(
    searchKeyword: String = "4일의 마라톤",
    onMedicineClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    // 샘플 데이터 (실제로는 ViewModel에서 가져올 예정)
    var medicines by remember {
        mutableStateOf(
            listOf(
                MedicineItem(
                    id = "1",
                    name = "ITEM_NAME",
                    nameEng = "[ITEM_ENG_NAME]",
                    className = "ENTP_NAME",
                    shape = "CLASS_NAME",
                    chart = "CHART",
                    isFavorite = false
                ),
                MedicineItem(
                    id = "2",
                    name = "ITEM_NAME",
                    nameEng = "[ITEM_ENG_NAME]",
                    className = "ENTP_NAME",
                    shape = "CLASS_NAME",
                    chart = "CHART",
                    isFavorite = false
                ),
                MedicineItem(
                    id = "3",
                    name = "ITEM_NAME",
                    nameEng = "[ITEM_ENG_NAME]",
                    className = "ENTP_NAME",
                    shape = "CLASS_NAME",
                    chart = "CHART",
                    isFavorite = true
                ),
                MedicineItem(
                    id = "4",
                    name = "ITEM_NAME",
                    nameEng = "[ITEM_ENG_NAME]",
                    className = "ENTP_NAME",
                    shape = "CLASS_NAME",
                    chart = "CHART",
                    isFavorite = false
                )
            )
        )
    }

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(medicines) { medicine ->
                MedicineResultCard(
                    medicine = medicine,
                    onCardClick = { onMedicineClick(medicine.id) },
                    onFavoriteClick = {
                        // 즐겨찾기 토글
                        medicines = medicines.map {
                            if (it.id == medicine.id) {
                                it.copy(isFavorite = !it.isFavorite)
                            } else {
                                it
                            }
                        }
                    }
                )
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
    medicine: MedicineItem,
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
                // ITEM_NAME [ITEM_ENG_NAME]
                Text(
                    text = "${medicine.name}  ${medicine.nameEng}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 회사명
                InfoText(label = "회사명", value = medicine.className)

                Spacer(modifier = Modifier.height(4.dp))

                // 분류
                InfoText(label = "분류", value = medicine.shape)

                Spacer(modifier = Modifier.height(4.dp))

                // 성상
                InfoText(label = "성상", value = medicine.chart)
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
                // 실제 이미지가 있을 때는 coil로 로드
                Image(
                    painter = painterResource(id = R.drawable.my_logo),
                    contentDescription = "Medicine Image",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // 오른쪽: 즐겨찾기 버튼
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (medicine.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = if (medicine.isFavorite) "즐겨찾기 해제" else "즐겨찾기 추가",
                    tint = if (medicine.isFavorite) Color(0xFFFFD700) else Color.Gray,
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

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview() {
    SearchResultScreen()
}