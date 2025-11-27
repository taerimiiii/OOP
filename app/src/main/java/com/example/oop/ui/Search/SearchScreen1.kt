package com.example.oop.ui.Search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import com.example.oop.ui.keyword.KeywordSearchScreen1
import com.example.oop.ui.medicineDetail.MedicineDetailScreen
import com.example.oop.ui.view.SearchResultScreen
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

data class Medicine(val name: String, val effect: String)
val allMedicines = listOf(
    Medicine("a", "두통 및 발열 완화"),
    Medicine("b", "상처 치료"),
    Medicine("c", "항생 효과")
)

@Composable
fun SearchTech(value: String,
               onValueChange: (String) -> Unit,
               onSearchExecuted: (String) -> Unit){
    val focusManager = LocalFocusManager.current


    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("제품명을 입력해주세요") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExecuted(value)
               focusManager.clearFocus()
            }
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearchExecuted(value)
                    focusManager.clearFocus()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search_mark),
                    contentDescription = "search button",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        },
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )

    )
}
@Composable
fun SearchScreen1(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var showKeywordSearchScreen by remember { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Medicine>>(emptyList()) }
    val executeSearch: (String) -> Unit = { query ->
        println("--- 🔎 검색 로직 시작. 쿼리 값: '$query' ---")
        if (query.isBlank()) {
            searchResults = emptyList()
            println("검색어 없음: 결과 초기화")
        } else {
            val results = allMedicines.filter {
                it.name.contains(query.trim(), ignoreCase = true)
            }

            searchResults = results
            println("검색 실행: '$query', 결과 ${results.size}개")
        }
    }

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
        showKeywordSearchScreen -> {
            KeywordSearchScreen1(modifier = modifier)
        }


        else -> {
            Column(
                modifier = Modifier.padding(top = 100.dp).fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "제품명 검색",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.wrapContentWidth(Alignment.End)
                    )
                    Spacer(modifier = Modifier.width(45.dp))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.line_select),
                            contentDescription = "select bar",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(35.dp))
                    TextButton(
                        onClick = { showKeywordSearchScreen = true },
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    ) {
                        Text(
                            text = "키워드 검색",
                            color = Color.Black
                        )
                    }
                }
                SearchTech(
                    value = searchText,
                    onValueChange = { searchText = it },
                    onSearchExecuted = executeSearch
                )
                Text(text = "--- 검색 결과 ---")
                if (searchResults.isNotEmpty()) {
                    searchResults.forEach { medicine ->
                        Text(text = "제품명: ${medicine.name}, 효능: ${medicine.effect}")
                    }
                } else {
                    Text(text = "검색 결과가 없습니다.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen1()
}