package com.example.oop.ui.Search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oop.ui.medicineDetail.MedicineDetailScreen

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }

    if (showDetailScreen) {
        MedicineDetailScreen(
            medicineId = "medicine_001",
            onBackClick = { showDetailScreen = false }
        )
    } else {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "검색 화면을 구성하세요.",
                    modifier = Modifier.padding(24.dp)
                )
                Button(
                    onClick = { showDetailScreen = true },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("약품 상세 보기")
                }
            }
        }
    }
}