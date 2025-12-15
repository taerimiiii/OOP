package com.example.oop.ui.Search

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
import com.example.oop.data.api.model.MedicineItem
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.example.oop.data.repository.MedicineRepository
import kotlinx.coroutines.launch

private const val MAX_CAPACITY = 5
private const val TAG = "SearchFeature"

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
fun SearchScreen(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var showKeywordSearchScreen by remember { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var searchResults by rememberSaveable {
        mutableStateOf<List<MedicineItem>>(emptyList())
    }
    var selectedItemSeq by remember { mutableStateOf<String?>(null) }
    val repository = remember { MedicineRepository() }
    val scope = rememberCoroutineScope()
    val savedSearchesList = rememberSaveable {
        mutableStateOf(emptyList<String>())
    }
    val recentSearches = remember {
        savedSearchesList.value.toMutableStateList()
    }

    val addSearchTerm: (String) -> Unit = remember {
        { term ->
            run label@{
                if (term.isBlank()) return@label

                recentSearches.remove(term)
                recentSearches.add(0, term)

                if (recentSearches.size > MAX_CAPACITY) {
                    recentSearches.removeAt(MAX_CAPACITY)
                }

                savedSearchesList.value = recentSearches.toList()
            }
        }
    }


    val removeSearchTerm: (String) -> Unit = { term ->
        recentSearches.remove(term)
        savedSearchesList.value = recentSearches.toList()
    }


    val executeSearch: (String) -> Unit = { query ->
        println("--- 🔎 검색 로직 시작. 쿼리 값: '$query' ---")


        addSearchTerm(query)

        if (query.isBlank()) {
            searchResults = emptyList()
            searchText = ""
            println("검색어 없음: 결과 초기화")
        } else {
            showSearchResultScreen = false
            isSearching = true
            scope.launch {
                try {
                    // 💡 DB 검색 실행
                    val results = repository.searchFromDatabase(query)

                    if (results.isNotEmpty()) {
                        searchResults = results
                        showSearchResultScreen = true
                        println("✅ DB 검색 성공, 검색된 결과 수: ${results.size}. SearchResultScreen으로 이동.")
                    } else {
                        searchResults = emptyList()
                        println("❌ DB 검색 결과 없음.")

                        // 💡 (선택 사항) DB에 없으면 API를 호출하여 저장 (최초 데이터 로딩 시 주로 사용)
                        // repository.fetchAndSaveMedicines(query)
                    }
                } catch (e: Exception) {
                    println("검색 중 오류 발생: ${e.message}")
                    searchResults = emptyList()
                } finally {
                    isSearching = false
                }
            }
        }
    }
    when {
        showDetailScreen && selectedItemSeq != null -> {
            MedicineDetailScreen(
                medicineId = selectedItemSeq!!,
                onBackClick = {
                    showDetailScreen = false
                    selectedItemSeq = null
                }
            )
        }

        showSearchResultScreen -> {
            // NOTE: 현재 SearchResultScreen 컴포넌트는 List<MedicineItem>을 받는 인자가 없으므로,
            // 이 화면 내부에서 결과를 표시하려면 SearchResultScreen 컴포넌트 자체를 수정해야 합니다.
            SearchResultScreen(
                // SearchResultScreen에서 항목 클릭 시 showDetailScreen을 true로 변경
                onMedicineClick = {
                    // 실제 구현에서는 클릭된 항목의 itemSeq를 여기에 저장해야 함
                    selectedItemSeq = "TODO: CLICKED_ITEM_SEQ" // 임시 값
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

                Text(
                    text = when {
                        isSearching -> "검색 중입니다..." // 💡 로딩 중일 때 메시지
                        searchText.isNotBlank() && searchResults.isEmpty() && !showSearchResultScreen ->
                            "검색 결과가 없거나 연관된 내용이 없습니다."
                        else ->
                            "제품명을 입력하고 검색 버튼을 누르세요."
                    },
                    modifier = Modifier
                        .padding(all = 15.dp)
                        .padding(bottom = 0.dp)
                )

                RecentSearchScreen(
                    recentSearches = recentSearches,
                    onSearchExecuted = { term ->
                        searchText = term
                        executeSearch(term)
                    },
                    onRemoveSearchTerm = removeSearchTerm
                )
            }
        }
    }
}

@Composable
fun RecentSearchScreen(
    recentSearches: List<String>,
    onSearchExecuted: (String) -> Unit,
    onRemoveSearchTerm: (String) -> Unit
) {


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

                        onSearchClicked = { onSearchExecuted(term) },

                        onRemove = { onRemoveSearchTerm(term) }
                    )
                }
            }
        }
    }
}


@Composable
fun SearchItem(
    term: String,
    onSearchClicked: (String) -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
    SearchScreen()
}