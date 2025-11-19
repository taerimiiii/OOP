package com.example.oop.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.oop.R

// 상단 바
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillTopBar(
    modifier: Modifier = Modifier,
    useGreenBackground: Boolean = true, // 연두색 바탕은 true, 하얀색 바탕은 false
) {
    val lightGreenColor = Color(0xFFE1EFC7)
    val whiteColor = Color(0xFFFFFFFF)
    val containerColor = if (useGreenBackground) {
                            lightGreenColor
                        } else {
                            whiteColor
                        }

    TopAppBar(
        modifier = modifier,
        title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),                 // 전체 너비 사용
                    horizontalArrangement = Arrangement.SpaceBetween,   // 양 끝 배치
                    verticalAlignment = Alignment.CenterVertically      // 수직 중앙 정렬
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(42.dp)
                    )

                    Text(
                        text = "MediLog",
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color(0xFF004A24)
                    )
                }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = Color(0xFF004A24)
        )
    )
}

// 하단 바
@Composable
fun BottomNavBar(
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    val greenColor = Color(0xFF71E000)
    val grayColor = Color(0xFFCDCDCD)
    val whiteColor = Color(0xFFFFFFFF)

    Column(modifier = Modifier.background(whiteColor)) {
        HorizontalDivider(
            color = grayColor.copy(alpha = 0.5f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier.height(80.dp),
            containerColor = whiteColor,
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = (selectedItem == 0),
                onClick = { onItemClick(0) },
                icon = {
                    Icon(
                    Icons.Default.Search,
                    "Search",
                    modifier = Modifier.size(33.dp)
                    )},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,     // 선택한 아이콘의 색
                    unselectedIconColor = grayColor,    // 선택 안 한 아이콘의 색
                    indicatorColor = Color.Transparent  // 뒷 배경 제거
                )
            )

            NavigationBarItem(
                selected = (selectedItem == 1),
                onClick = { onItemClick(1) },
                icon = {
                    Icon(
                        Icons.Default.Home,
                        "Home",
                        modifier = Modifier.size(33.dp)
                    )},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = grayColor,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = (selectedItem == 2),
                onClick = { onItemClick(2) },
                icon = {
                    Icon(
                        Icons.Default.DateRange,
                        "Calendar",
                        modifier = Modifier.size(33.dp)
                    )},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = greenColor,
                    unselectedIconColor = grayColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
