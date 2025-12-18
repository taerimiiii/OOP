package com.example.oop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oop.ui.BottomNavBar
import com.example.oop.ui.PillTopBar
import com.example.oop.ui.Search.SearchScreen
import com.example.oop.ui.calendar.CalendarJoinScreen
import com.example.oop.ui.home.HomeScreen
import com.example.oop.ui.onBoarding.MainScreen
import com.example.oop.ui.theme.OOPTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OOPTheme(darkTheme = false) {
                val selectedBottomItem = remember { mutableStateOf(1) }
                val isLoggedIn = remember { mutableStateOf(false) }

                // 로그인되지 않았을 때는 onBoarding 화면만 표시 (상단바/하단바 없음)
                if (!isLoggedIn.value) {
                    MainScreen(
                        onLoginSuccess = {
                            isLoggedIn.value = true
                        }
                    )
                } else {
                    // 로그인되었을 때는 메인 화면 표시 (상단바/하단바 있음)
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
                                selectedItem = selectedBottomItem.value,
                                onItemClick = { selectedBottomItem.value = it }
                            )
                        }
                    ) { innerPadding ->
                        Content(
                            selectedItem = selectedBottomItem.value,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

// 화면 관리 컴포저블
// 메인에 넣어도 되나...??
@Composable
fun Content(
    selectedItem: Int,
    modifier: Modifier = Modifier
) {
    when (selectedItem) {
        0 -> SearchScreen(modifier = modifier)
        1 -> HomeScreen(modifier = modifier)
        2 -> CalendarJoinScreen(modifier = modifier)
        else -> HomeScreen(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    OOPTheme {
        MainScreen()
    }
}