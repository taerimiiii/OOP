package com.example.oop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oop.ui.BottomNavBar
import com.example.oop.ui.BottomNavDestination
import com.example.oop.ui.PillTopBar
import com.example.oop.ui.Search.SearchScreen
import com.example.oop.ui.calender.CalendarScreen
import com.example.oop.ui.home.HomeScreen
import com.example.oop.ui.theme.OOPTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OOPTheme(darkTheme = false) {
                val navController = rememberNavController()
                val bottomDestinations = remember { listOf(
                        BottomNavDestination.Search,
                        BottomNavDestination.Home,
                        BottomNavDestination.Calendar,
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // 상단 바 호출
                    topBar = {
                        PillTopBar(
                            useGreenBackground = true
                        )
                    },
                    // 하단 바 호출
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,      // 화면 이동 담당
                            destinations = bottomDestinations   // 하단바에 표시할 목적지 리스트 (롤백을 해도 이상한 곳을 갈 가능성 줄어듦)
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,  // 화면 이동
                        startDestination = BottomNavDestination.Home.route, // 앱 최초 진입 화면 = Home
                        modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                        // 검색 화면
                        composable(BottomNavDestination.Search.route) {
                            SearchScreen()
                        }
                        // 홈 화면
                        composable(BottomNavDestination.Home.route) {
                            HomeScreen()
                        }
                        // 캘린더 화면
                        composable(BottomNavDestination.Calendar.route) {
                            CalendarScreen()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    OOPTheme {
        HomeScreen()
    }
}