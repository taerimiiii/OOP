package com.example.oop.ui.keyword

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import com.example.oop.ui.Search.SearchScreen
import com.example.oop.ui.medicineDetail.MedicineDetailScreen
import com.example.oop.ui.view.SearchResultScreen
import androidx.compose.ui.unit.IntSize

data class DetailResult(
    val letter: String = "_",
    val type: String = "_",
    val pill_shape: String = "_",
    val pill_color: String = "_"
)

@Composable
fun KeywordSearchScreen1(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var showKeywordSearchScreen by remember { mutableStateOf(false) }
    var showSearchScreen by remember { mutableStateOf(false) }
    var submittedQuery by remember { mutableStateOf("") }
    var bottomBarHeight by remember { mutableStateOf(IntSize.Zero) }

    // 2. 검색을 실행하는 함수 정의
    val performSearch: (String) -> Unit = { query ->
        submittedQuery = query // 제출된 검색어 저장

        // 🚨 핵심 로직: 검색 실행
        if (query.isNotBlank()) {
            println("검색 실행! 쿼리: $query")
            // TODO:
            // 1. 네비게이션을 사용하여 검색 결과 화면(SearchResultScreen)으로 이동
            // 2. ViewModel의 loadResults(query) 함수 호출
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
        showSearchScreen -> { SearchScreen(modifier = modifier) }


        else -> {
            Column(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .fillMaxSize()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { showSearchScreen = true },
                        modifier = Modifier.wrapContentWidth(Alignment.End)
                    ) {
                        Text(
                            text = "제품명 검색",
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(35.dp))
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
                    Spacer(modifier = Modifier.width(45.dp))
                    Text(
                        text = "키워드 검색",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.wrapContentWidth(Alignment.Start)
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxWidth().padding(15.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 3f
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.padding(bottom = 80.dp)
                ) {
                    item { Keyword_letter(onSearch = performSearch) }
                    item { Keyword_type() }
                    item { Keyword_shape() }
                    item { Keyword_color() }
                    item {Decide_reset()}
                }
            }
        }
    }
}

@Composable
//각인 입력칸
fun Keyword_letter(
    onSearch: (String) -> Unit
){
    var searchText by remember { mutableStateOf("") }
    Text(
        modifier = Modifier.padding( horizontal = 25.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            append("앞면이나 뒷면의 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("각인")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("각인을 입력하세요") },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                // 입력 내용이 있을 경우 지우기 버튼 표시
                IconButton(onClick = { searchText = "" }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear search")
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchText)
            }
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
//제형 선택칸
fun Keyword_type(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.padding( 25.dp, 10.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("제형")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_tablet"
        )
        Spacer(Modifier.width(35.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_hard"
        )
        Spacer(Modifier.width(35.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_soft"
        )
    }
}

@Composable
//모양 선택칸
fun Keyword_shape(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.padding( 25.dp, 15.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("모양")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_circle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_oval"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_rectangle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_circle"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_try"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_rectangle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_tilt_rectangle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_penta"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_try"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_rectangle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp).alpha(0f),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_tilt_rectangle"
        )
        Spacer(Modifier.width(8.dp))
        Image(
            modifier = Modifier.size(90.dp).alpha(0f),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_penta"
        )
    }
}

@Composable
//색상 선택칸
fun Keyword_color(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.padding( 25.dp, 15.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("색상")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_circle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_oval"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_circle"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_try"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_tilt_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_penta"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_hexa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_octa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_hexa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_octa"
        )
    }
}

@Composable
fun Decide_reset() {

    // 1. Box 컨테이너를 사용하여 이미지 위에 다른 요소(버튼)를 겹쳐 놓습니다.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(100.dp) // 전체 컨테이너의 높이를 이미지에 맞게 설정
    ) {

        // 2. 직사각형 컨테이너 역할을 할 배경 이미지 (가장 아래에 배치)
        Image(
            painter = painterResource(R.drawable.keyword_underbar),
            contentDescription = "배경 직사각형 이미지",
            contentScale = ContentScale.FillBounds, // Box 크기에 맞게 이미지 늘리기
            modifier = Modifier.matchParentSize() // 부모 Box의 크기(150dp)를 따름
        )

        // 3. 버튼들을 수평으로 배치하기 위한 Row (이미지 위에 겹쳐짐)
        Row(
            modifier = Modifier
                .fillMaxSize() // 이미지와 같은 크기
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 첫 번째 타원형 버튼
            Image(
                modifier = Modifier.width(200.dp).height(70.dp),
                painter =  painterResource(R.drawable.rectangle_finish),
                contentDescription = "keyword_shape_try"
            )

            // 두 번째 타원형 버튼
            Image(
                modifier = Modifier.width(90.dp).height(90.dp),
                painter =  painterResource(R.drawable.rectangle_reset),
                contentDescription = "keyword_shape_try"
            )
        }
    }
}

@Preview(showBackground = true,
    widthDp = 360,
    heightDp = 1500)
@Composable
fun KeywordScreenPreview() {
    KeywordSearchScreen1()
}