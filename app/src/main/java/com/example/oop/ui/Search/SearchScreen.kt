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
import com.example.oop.ui.view.SearchResultScreen

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }

    var selectedMedicineId by remember { mutableStateOf("") }


    when {
        showDetailScreen -> {
            MedicineDetailScreen(
                medicineId = selectedMedicineId,
                onBackClick = { showDetailScreen = false }
            )
        }

        showSearchResultScreen -> {
            SearchResultScreen(
                searchKeyword = "타이레놀",
                onMedicineClick = { itemSeq ->
                    android.util.Log.d("SearchScreen", "클릭한 의약품: $itemSeq")
                    selectedMedicineId = itemSeq
                    showSearchResultScreen = false
                    showDetailScreen = true
                },
                onBackClick = { showSearchResultScreen = false }
            )
        }

        else -> {
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
                        onClick = { showSearchResultScreen = true },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("의약품 검색 결과 페이지")
                    }
                    Button(
                        onClick = { showDetailScreen = true },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("의약품 디테일 페이지")
                    }
                }
            }
        }
    }
}