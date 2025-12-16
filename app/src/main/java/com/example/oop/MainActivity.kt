package com.example.oop

import com.example.oop.ui.theme.PillHomeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// 팀원들의 UI 컴포넌트들
import com.example.oop.ui.BottomNavBar
import com.example.oop.ui.PillTopBar
import com.example.oop.ui.Search.SearchScreen
import com.example.oop.ui.calendar.CalendarJoinScreen
import com.example.oop.ui.onBoarding.MainScreen
import com.example.oop.ui.theme.OOPTheme

// 님이 만든(이사 온) 파일들 (패키지 경로가 com.example.oop.ui 라고 가정)
import com.example.oop.ui.*


// 화면 주소 (Enum)
enum class Screen {
    Loading,            // 1. 앱 로딩
    Login, FindPassword, // 2. 로그인 관련
    Join1, Join2, Join2_1, Join3, Join4, // 3. 회원가입 단계 (1~4단계)
    MainContentLoading, // 4. 메인 진입 전 로딩
    Home                // 5. 진짜 메인 (상단바+하단바 있는 곳)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 팀원 코드에 있던 엣지투엣지 유지

        setContent {
            // 팀의 테마를 전체에 적용
            OOPTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. 네비게이션 컨트롤러 (길안내 도우미)
                    val navController = rememberNavController()

                    // 2. 전체 화면 흐름 관리 (로그인 -> 메인)
                    NavHost(navController = navController, startDestination = Screen.Loading.name) {

                        // [1] 앱 시작 로딩
                        composable(Screen.Loading.name) {
                            LoadingScreen(
                                onLoadingFinished = {
                                    navController.navigate(Screen.Login.name) {
                                        popUpTo(Screen.Loading.name) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // [2] 로그인 화면
                        composable(Screen.Login.name) {
                            LoginScreen(
                                onLoginClick = {
                                    // 로그인 성공 -> 메인 로딩으로 이동
                                    navController.navigate(Screen.MainContentLoading.name)
                                },
                                onJoinClick = {
                                    // 회원가입 1단계로 이동
                                    navController.navigate(Screen.Join1.name)
                                },
                                onFindPasswordClick = {
                                    // 비밀번호 찾기로 이동
                                    navController.navigate(Screen.FindPassword.name)
                                }
                            )
                        }

                        // [3] 메인 진입 전 로딩 (Pill 로고)
                        composable(Screen.MainContentLoading.name) {
                            MainContentLoadingScreen(
                                onLoadingFinished = {
                                    navController.navigate(Screen.Home.name) {
                                        popUpTo(Screen.Login.name) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // [4] 진짜 메인 화면 (여기에 팀원들의 Scaffold 코드를 넣음!)
                        composable(Screen.Home.name) {
                            PillHomeScreen()
                            // --- 여기서부터 팀원 코드 (상단바, 하단바) ---
                            val selectedBottomItem = remember { mutableStateOf(1) }

                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                topBar = {
                                    PillTopBar(useGreenBackground = true)
                                },
                                bottomBar = {
                                    BottomNavBar(
                                        selectedItem = selectedBottomItem.value,
                                        onItemClick = { selectedBottomItem.value = it }
                                    )
                                }
                            ) { innerPadding ->
                                // Scaffold 안의 내용물 (검색, 홈, 캘린더 전환)
                                Content(
                                    selectedItem = selectedBottomItem.value,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            // --- 팀원 코드 끝 ---
                        }

                        // [5] 회원가입 화면들 (1 -> 2 -> 2_1 -> 3 -> 4 순서 연결)
                        composable(Screen.Join1.name) {
                            JoinScreen1(
                                onNextClick = { navController.navigate(Screen.Join2.name) },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.Join2.name) {
                            JoinScreen2(
                                onNextClick = { navController.navigate(Screen.Join2_1.name) },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.Join2_1.name) {
                            JoinScreen2_1(
                                onNextClick = { navController.navigate(Screen.Join3.name) },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.Join3.name) {
                            JoinScreen3(
                                onNextClick = { navController.navigate(Screen.Join4.name) },
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                        composable(Screen.Join4.name) {
                            // JoinScreen3에서 받은 정보를 넘겨줘야 하지만, UI 확인을 위해 빈 값으로 연결
                            JoinScreen4(
                                onFinishClick = {
                                    // 회원가입 완료 시 로그인 화면으로 복귀 (백스택 비움)
                                    navController.navigate(Screen.Login.name) {
                                        popUpTo(0)
                                    }
                                },
                                email = "",
                                password = "",
                                name = "",
                                phoneNumber = ""
                            )
                        }

                        // [6] 비밀번호 찾기
                        composable(Screen.FindPassword.name) {
                            FindPasswordScreen(
                                onBackClick = { navController.popBackStack() },
                                onFinishClick = {
                                    navController.navigate(Screen.Login.name) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 팀원들이 만들어둔 화면 전환 로직 (그대로 유지)
@Composable
fun Content(
    selectedItem: Int,
    modifier: Modifier = Modifier
) {
    when (selectedItem) {
        0 -> SearchScreen(modifier = modifier)
        1 -> MainScreen() // 여기서 MainScreen은 팀원이 만든 메인 내용(onBoarding 쪽)
        2 -> CalendarJoinScreen(modifier = modifier)
        else -> MainScreen()
    }
}