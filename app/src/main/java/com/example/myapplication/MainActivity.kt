package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MainScreen // 우리가 방금 정리한 MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

// 화면들의 주소(이름)를 정해두는 곳
enum class Screen {
    Loading,            // 1. 앱 켜자마자 나오는 로딩
    Login,              // 2. 로그인 화면
    Join1, Join2, Join2_1, Join3, Join4, // 회원가입 단계들
    MainContentLoading, // 3. 로그인 성공 후 나오는 로고 로딩
    Home                // 4. 최종 메인 화면 (MainScreen.kt)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. 네비게이션 컨트롤러 생성 (길안내 도우미)
                    val navController = rememberNavController()

                    // 2. 화면 이동 경로 정의 (여기서 모든 길을 관리함)
                    NavHost(navController = navController, startDestination = Screen.Loading.name) {

                        // [1] 앱 시작 로딩
                        composable(Screen.Loading.name) {
                            LoadingScreen(
                                onLoadingFinished = {
                                    // 로딩 끝나면 로그인 화면으로
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
                                    // 로그인 성공 -> 메인 진입 전 로딩(로고)으로 이동
                                    navController.navigate(Screen.MainContentLoading.name)
                                },
                                onJoinClick = {
                                    // 회원가입 버튼 -> 가입 1단계로 이동
                                    navController.navigate(Screen.Join1.name)
                                }
                            )
                        }

                        // [3] 메인 진입 전 로딩 (Pill 로고)
                        composable(Screen.MainContentLoading.name) {
                            MainContentLoadingScreen(
                                onLoadingFinished = {
                                    // 로딩 끝나면 -> 진짜 메인 화면(Home)으로 이동!
                                    navController.navigate(Screen.Home.name) {
                                        // 로그인 화면으로 뒤로가기 못하게 기록 삭제
                                        popUpTo(Screen.Login.name) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // [4] 진짜 메인 화면 (MainScreen.kt 파일 내용)
                        composable(Screen.Home.name) {
                            MainScreen() // 이제 여기서 깨끗해진 MainScreen을 불러옵니다.
                        }

                        // [5] 회원가입 화면들
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
                            JoinScreen4(
                                onFinishClick = {
                                    // 회원가입 완료 -> 로그인 화면으로 (기록 삭제)
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
                    }
                }
            }
        }
    }
}