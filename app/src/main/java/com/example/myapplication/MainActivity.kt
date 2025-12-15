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
import com.example.myapplication.ui.theme.MainScreen // MainScreen 파일 경로 확인 필요
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. 화면 이동을 관리하는 '컨트롤러'를 만듭니다.
                    val navController = rememberNavController()

                    // 2. NavHost: 여기서 화면 이동 규칙을 정합니다.
                    // startDestination = "Loading": 앱을 켜면 "Loading"이라는 화면부터 보여줘라!
                    NavHost(navController = navController, startDestination = "Loading") {

                        // [규칙 1] "Loading" 화면
                        composable("Loading") {
                            // LoadingScreen을 보여줍니다.
                            LoadingScreen(
                                onLoadingFinished = {
                                    // 로딩이 끝나면 "Main"으로 이동해라!
                                    // popUpTo("Loading") { inclusive = true }: 뒤로가기 눌렀을 때 로딩화면 다시 안 나오게 삭제
                                    navController.navigate("Main") {
                                        popUpTo("Loading") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // [규칙 2] "Main" 화면
                        composable("Main") {
                            // MainScreen(메인 화면 관리자)을 실행해라!
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}