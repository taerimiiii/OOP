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

sealed class BottomNavDestination(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val contentDescription: String
) {
    data object Search : BottomNavDestination(
        route = "search",
        icon = Icons.Default.Search,
        contentDescription = "Search"
    )

    data object Home : BottomNavDestination(
        route = "home",
        icon = Icons.Default.Home,
        contentDescription = "Home"
    )

    data object Calendar : BottomNavDestination(
        route = "calendar",
        icon = Icons.Default.DateRange,
        contentDescription = "Calendar"
    )
}

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
// NavHost를 사용하는 Compose Navigation을 도입함.
@Composable
fun BottomNavBar(
    navController: NavHostController,           // 네비게이션 이동에 사용되는 NavController
    destinations: List<BottomNavDestination>    // BottomNav 에 표시할 아이콘/라우트 목록
) {
    val greenColor = Color(0xFF71E000)
    val grayColor = Color(0xFFCDCDCD)
    val whiteColor = Color(0xFFFFFFFF)

    // 현재 Navigation 상태(현재 어떤 화면이 활성화인지 확인)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // 현재 화면의 NavDestination
    val currentDestination = navBackStackEntry?.destination

    Column(
        modifier = Modifier.background(whiteColor)
    ) {
        // 상단 구분선
        HorizontalDivider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier.height(80.dp),
            containerColor = whiteColor,
        ) {
            // 전달받은 destination 리스트만큼 NavigationBarItem 반복 생성
            destinations.forEach { destination ->

                // 현재 화면이 destination.route 와 일치하는지 확인
                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true

                // Bottom Navigation 단일 아이템
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) { // 이미 선택된 화면이면 이동하지 않음
                            navController.navigate(destination.route) {
                                // 시작 목적지까지 pop 하여 중복 쌓임 방지 + 상태 저장
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true  // 동일 루트 중복 방지
                                restoreState = true     // 이전 상태 복원
                            }
                        }
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            destination.contentDescription,
                            modifier = Modifier.size(33.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = greenColor,     // 선택한 아이콘의 색
                        unselectedIconColor = grayColor,    // 선택 안 한 아이콘의 색
                        indicatorColor = Color.Transparent  // 뒷 배경 제거
                    )
                )
            }
        }
    }
}
