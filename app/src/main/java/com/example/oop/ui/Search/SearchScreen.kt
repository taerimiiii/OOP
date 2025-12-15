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
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val MAX_CAPACITY = 5
private const val TAG = "SearchFeature"

private val RecentSearchSaver: Saver<SnapshotStateList<String>, List<String>> = Saver(
    save = { it.toList() }, // SnapshotStateList<String>을 List<String>으로 변환
    restore = { it.toMutableStateList() } // List<String>을 SnapshotStateList<String>으로 복원
)

data class MedicineItem(
    val itemSeq: String, // **Key: 제품일련번호 (반환 목표)**
    val itemName: String, // **Key: 제품명 (검색어와 비교 목표)**
    val drugShape: String, // 제형
    val color1: String, // 색상
    val printFront: String?, // 앞면 각인 (null 허용)
    val printBack: String? // 뒷면 각인 (null 허용)
)

val allMedicines = listOf(
    MedicineItem(
        itemSeq = "200808876",
        itemName = "가스디알정50밀리그램(디메크로틴산마그네슘)",
        drugShape = "원형",
        color1 = "연두",
        printFront = "IDG",
        printBack = null
    ),
    MedicineItem(
        itemSeq = "199401777",
        itemName = "타이레놀정500밀리그램",
        drugShape = "장방형",
        color1 = "흰색",
        printFront = "TYL",
        printBack = "500"
    ),
    MedicineItem(
        itemSeq = "202008711",
        itemName = "이지엔6프로연질캡슐",
        drugShape = "타원형",
        color1 = "노란색",
        printFront = null,
        printBack = null
    ),
    MedicineItem(
        itemSeq = "199401778",
        itemName = "타이레놀정160밀리그램", // <-- 검색 목표 2
        drugShape = "장방형",
        color1 = "흰색",
        printFront = "TYL",
        printBack = "160"
    ),
    MedicineItem(
        itemSeq = "200600001",
        itemName = "타이레놀콜드-에스정", // <-- 연관 검색 목표 3
        drugShape = "타원형",
        color1 = "흰색",
        printFront = "T-C",
        printBack = "S"
    )
)

suspend fun searchItem(query: String): List<MedicineItem> {
    delay(500) // 0.5초 대기 (비동기 작업 시뮬레이션)

    if (query.isBlank()) return emptyList()

    val normalizedQuery = query.trim()

    // 검색어가 약품명에 부분적으로 포함되는 모든 항목을 찾습니다. (Case Insensitive)
    val foundMedicines = allMedicines.filter {
        it.itemName.contains(normalizedQuery, ignoreCase = true)
    }

    return foundMedicines
}

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
    var searchResults by remember { mutableStateOf<List<MedicineItem>>(emptyList()) }
    var selectedItemSeq by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope() // CoroutineScope 추가
    val recentSearches = rememberSaveable(saver = RecentSearchSaver) {
        mutableStateListOf<String>()
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
            }
        }
    }

    // 💡 변경 3: 개별 검색어 제거 로직
    val removeSearchTerm: (String) -> Unit = { term ->
        recentSearches.remove(term)
    }

    // 변경점 3: executeSearch 로직을 apiResultItemSeq에 맞게 수정
    val executeSearch: (String) -> Unit = { query ->
        println("--- 🔎 검색 로직 시작. 쿼리 값: '$query' ---")

        // 검색 실행 시 무조건 최근 검색어 목록 업데이트
        addSearchTerm(query)

        if (query.isBlank()) {
            searchResults = emptyList()
            searchText = ""
            println("검색어 없음: 결과 초기화")
        } else {
            scope.launch {
                val results = searchItem(query)

                if (results.isNotEmpty()) {
                    searchResults = results
                    showSearchResultScreen = true
                    println("✅ API 호출 성공, 검색된 결과 수: ${results.size}. SearchResultScreen으로 이동.")
                } else {
                    searchResults = emptyList()
                    println("❌ 검색 결과 없음")
                }
            }
        }
    }
    when {
        showDetailScreen && selectedItemSeq != null -> {
            MedicineDetailScreen(
                medicineId = selectedItemSeq!!, // itemSeq 전달
                onBackClick = {
                    showDetailScreen = false
                    selectedItemSeq = null // 화면 복귀 시 itemSeq 초기화
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
                    text = if (searchText.isNotBlank() && searchResults.isEmpty() && !showSearchResultScreen)
                        "검색 결과가 없거나 연관된 내용이 없습니다."
                    else "제품명을 입력하고 검색 버튼을 누르세요.",
                    modifier = Modifier
                        .padding(all = 15.dp) // 상하좌우 모두 10dp를 먼저 적용
                        .padding(bottom = 0.dp)
                )

                RecentSearchScreen(
                    recentSearches = recentSearches, // 상태 목록 전달
                    onSearchExecuted = { term ->
                        searchText = term // 검색 필드 업데이트
                        executeSearch(term) // 검색 실행 (내부에서 addSearchTerm 호출됨)
                    },
                    onRemoveSearchTerm = removeSearchTerm // 개별 삭제 함수 전달
                )
            }
        }
    }
}

@Composable
fun RecentSearchScreen(
    // 💡 변경 7: 상태 목록을 인자로 받음
    recentSearches: List<String>,
    onSearchExecuted: (String) -> Unit, // 검색 실행 (SearchScreen의 executeSearch로 연결됨)
    onRemoveSearchTerm: (String) -> Unit // 개별 삭제 (SearchScreen의 removeSearchTerm으로 연결됨)
) {
    // 💡 로직 제거: rememberSaveable, addSearchTerm, performSearch, LaunchedEffect 모두 제거

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
                        // 💡 변경 8: 클릭 시 onSearchExecuted에 term을 전달하여 바로 호출
                        onSearchClicked = { onSearchExecuted(term) },
                        // 💡 변경 9: 삭제 시 onRemoveSearchTerm에 term을 전달하여 바로 호출
                        onRemove = { onRemoveSearchTerm(term) }
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
    SearchScreen()
}