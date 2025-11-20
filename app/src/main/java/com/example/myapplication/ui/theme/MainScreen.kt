package com.example.myapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.JoinScreen1
import com.example.myapplication.JoinScreen2
import com.example.myapplication.JoinScreen2_1
import com.example.myapplication.JoinScreen3
import com.example.myapplication.JoinScreen4

// 다른 파일에 있는 화면들을 가져오기 위한 import
import com.example.myapplication.LoginScreen
import com.example.myapplication.LoadingScreen
import com.example.myapplication.MainContentLoadingScreen
// JoinScreen이 만약 ui.theme 패키지가 아니라면 아래 주석을 풀거나 자동으로 import 됩니다.
// import com.example.myapplication.JoinScreen1
// (같은 ui.theme 패키지에 있다면 import가 필요 없습니다)

// 화면 경로를 정의하는 enum 클래스
enum class Screen {
    Login,
    Loading,
    Join1,
    Join2,
    Join2_1,
    Join3,
    Join4,
    MainContentLoading // 메인 화면 진입 전 로딩
}

@Composable
fun MainScreen() {
    val navController = rememberNavController() // 화면 전환을 관리하는 컨트롤러

    NavHost(navController = navController, startDestination = Screen.Login.name) {
        // 1. 로그인 화면
        composable(Screen.Login.name) {
            LoginScreen(
                onLoginClick = { navController.navigate(Screen.Loading.name) },
                onJoinClick = { navController.navigate(Screen.Join1.name) }
            )
        }

        // 2. 로딩 화면 (로그인 직후)
        composable(Screen.Loading.name) {
            LoadingScreen(
                onLoadingFinished = { navController.navigate(Screen.MainContentLoading.name) }
            )
        }

        // 3. 메인 진입 전 로딩 화면 (Pill 로고)
        composable(Screen.MainContentLoading.name) {
            // LoadingScreen.kt 파일에 있는 MainContentLoadingScreen 함수를 사용합니다.
            MainContentLoadingScreen(
                onLoadingFinished = { /* TODO: 실제 메인 화면으로 이동 */ }
            )
        }

        // 4. 회원가입 화면들
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
                    // 회원가입 완료 시 로그인 화면으로 돌아가되, 백스택을 모두 지워서 뒤로가기 못하게 함
                    navController.navigate(Screen.Login.name) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}