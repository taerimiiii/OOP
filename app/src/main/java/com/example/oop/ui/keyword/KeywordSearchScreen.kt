package com.example.oop.ui.keyword

import android.widget.Button
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import com.example.oop.ui.Search.SearchScreen1
import com.example.oop.ui.medicineDetail.MedicineDetailScreen
import com.example.oop.ui.view.SearchResultScreen

@Composable
fun KeywordSearchScreen1(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var showKeywordSearchScreen by remember { mutableStateOf(false) }
    var showSearchScreen by remember { mutableStateOf(false) }

    when {
        showDetailScreen -> {
            MedicineDetailScreen(
                medicineId = "medicine_001",
                onBackClick = { showDetailScreen = false }
            )
        }

        showSearchResultScreen -> {
            SearchResultScreen(
                onMedicineClick = {
                    showDetailScreen = true
                },
                onBackClick = { showSearchResultScreen = false }
            )
        }
        showSearchScreen -> { SearchScreen1(modifier = modifier) }


        else -> {
            Row(
                modifier = modifier.padding(40.dp).offset(x = 30.dp)
            ) {
                TextButton(
                    onClick = { showSearchScreen = true },
                    modifier = Modifier.offset(x = (-5).dp, y = (-12).dp)
                ){
                    Text(
                        text = "제품명 검색",
                        color = Color.Black
                    )
                }
                Box(
                    modifier = Modifier.weight(1f) // Row 공간을 최대로 차지하게 하여 중앙 공간 확보
                ) {
                    Image(
                        painter = painterResource(R.drawable.line_select),
                        contentDescription = "select bar",
                        modifier = Modifier.size(40.dp)
                            .offset(y = (-10).dp)
                            .align(Alignment.Center)
                    )
                }
                Spacer(Modifier.width(35.dp))
                Text(
                    text = "키워드 검색",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.width(100.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Canvas(modifier = Modifier.size(width = 350.dp, height = 250.dp).offset(y= (-300).dp)) {
                    drawLine(
                        color = Color.Black,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 3f
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KeywordScreenPreview() {
    KeywordSearchScreen1()
}