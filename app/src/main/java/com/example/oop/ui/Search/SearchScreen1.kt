package com.example.oop.ui.Search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

private const val MAX_CAPACITY = 5
private const val TAG = "SearchFeature"

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
                //Text(text = "--- 검색 결과 ---")
                //if (searchResults.isNotEmpty()) {
                //    searchResults.forEach { medicine ->
                //       Text(text = "제품명: ${medicine.name}, 효능: ${medicine.effect}")
                //    }
                //} else {
                //    Text(text = "검색 결과가 없습니다.")
                //}
                RecentSearchScreen()
            }
        }
    }
}

@Composable
fun RecentSearchScreen() {
    val recentSearches = rememberSaveable {
        mutableStateListOf("아이폰", "갤럭시", "노트북", "키보드", "마우스")
    }

    val addSearchTerm: (String) -> Unit = { term ->

        recentSearches.remove(term)
        recentSearches.add(0, term)

        if (recentSearches.size > MAX_CAPACITY) {
            recentSearches.removeAt(recentSearches.size - 1)
        }
    }

    // --- (1) 검색 실행 로직 (검색 버튼 클릭 시 호출될 함수) ---
    val performSearch: (String) -> Unit = { term ->
        Log.d(TAG, "검색 실행: $term")
        // 실제 검색 API 호출, 화면 이동, 결과 표시 등의 로직이 여기에 들어갑니다.
        // 여기서는 예시로 로그만 출력합니다.

        // 검색어를 최근 검색 목록에 추가하여 최신화합니다.
        addSearchTerm(term)
    }


    // 개별 검색어 제거 함수
    val removeSearchTerm: (String) -> Unit = { term ->
        recentSearches.remove(term)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "최근 검색어",
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 12.sp
        )

        // --- 검색어 목록 표시 ---
        if (recentSearches.isEmpty()) {
            Text(
                text = "최근 검색 기록이 없습니다.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(recentSearches) { term ->
                    SearchItem(
                        term = term,
                        // --- (2) SearchItemRow에 검색 실행 콜백 전달 ---
                        onSearchClicked = { clickedTerm ->
                            performSearch(clickedTerm)
                        },
                        onRemove = { removeSearchTerm(term) }
                    )
                }
            }
        }
    }
}

// --- (3) SearchItemRow 컴포저블 수정 ---
@Composable
fun SearchItem(
    term: String,
    onSearchClicked: (String) -> Unit, // 검색어 클릭 시 실행할 람다
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            // Row 클릭 시, 전달받은 onSearchClicked 람다를 실행합니다.
            .clickable { onSearchClicked(term) }
            .padding(vertical = 12.dp, horizontal = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = term,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "삭제"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen1()
}