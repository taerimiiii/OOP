package com.example.oop.ui.keyword

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.LaunchedEffect
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
    val letter: String = "",
    val type: String = "",
    val shape: String = "",
    val color: String = ""
)

@Composable
fun KeywordSearchScreen1(modifier: Modifier = Modifier) {
    // 임시 검색 UI
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var showKeywordSearchScreen by remember { mutableStateOf(false) }
    var selectedDetails by remember { mutableStateOf(DetailResult()) }
    var finalResultList by remember { mutableStateOf<List<DetailResult>>(emptyList()) }
    var showSearchScreen by remember { mutableStateOf(false) }
    var submittedQuery by remember { mutableStateOf("") }
    var bottomBarHeight by remember { mutableStateOf(IntSize.Zero) }

    // 2. 검색을 실행하는 함수 정의
    val performKeywordSearch: () -> Unit = {
        // 💡 [핵심] 현재 선택된 DetailResult 객체를 리스트에 담아 저장합니다.
        finalResultList = listOf(selectedDetails)

        println("--- 🔎 키워드 검색 결정 완료 ---")
        println("최종 결합된 DetailResult: ${selectedDetails}")
        println("최종 반환 리스트: $finalResultList")

        // 검색 결과 화면으로 임시 전환 (전환 시 이 리스트를 SearchResultScreen에 전달해야 함)
        showSearchResultScreen = true
    }

    val resetKeywordSearch: () -> Unit = {
        selectedDetails = DetailResult()
        finalResultList = emptyList()
        println("--- 🔎 키워드 검색 초기화 완료 ---")
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxWidth()
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
                    item { Keyword_letter(
                        initialSearchText = selectedDetails.letter, // 현재 값 전달
                        onSearch = { letter ->
                            selectedDetails = selectedDetails.copy(letter = letter) // letter만 업데이트
                        }
                    ) }

                    // 💡 [수정] 제형 값 업데이트 콜백 추가
                    item { Keyword_type(
                        selectedType = selectedDetails.type,
                        onTypeSelected = { type ->
                            selectedDetails = selectedDetails.copy(type = type) // type만 업데이트
                        }
                    ) }

                    // 💡 [수정] 모양 값 업데이트 콜백 추가
                    item { Keyword_shape(
                        selectedShape = selectedDetails.shape,
                        onShapeSelected = { shape ->
                            selectedDetails = selectedDetails.copy(shape = shape) // shape만 업데이트
                        }
                    ) }

                    // 💡 [수정] 색상 값 업데이트 콜백 추가
                    item { Keyword_color(
                        selectedColor = selectedDetails.color,
                        onColorSelected = { color ->
                            selectedDetails = selectedDetails.copy(color = color) // color만 업데이트
                        }
                    ) }

                    // 💡 [수정] 결정/리셋 로직 연결
                    item { Decide_reset(
                        onDecide = performKeywordSearch, // 최종 결정
                        onReset = resetKeywordSearch     // 초기화
                    ) }
                }
            }
        }
    }
}

@Composable
//각인 입력칸
fun Keyword_letter(
    initialSearchText: String, // 초기값 추가
    onSearch: (String) -> Unit
){
    var searchText by remember { mutableStateOf(initialSearchText) }
    LaunchedEffect(initialSearchText) {
        if (initialSearchText != searchText) {
            searchText = initialSearchText
        }
    }
    Text(
        modifier = Modifier.padding( top = 15.dp, start = 25.dp, end = 25.dp).fillMaxWidth(),
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
        onValueChange = {
            searchText = it
            onSearch(it) },
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
            .padding(horizontal = 16.dp, vertical = 0.dp)
    )
}

@Composable
//제형 선택칸
fun Keyword_type(
    selectedType: String, // 현재 선택된 상태를 받음
    onTypeSelected: (String) -> Unit, // 선택 시 콜백
    modifier: Modifier = Modifier
){
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
        val typeTablet = "정제"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onTypeSelected(typeTablet) },
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedType == typeTablet) 1f else 0.5f
        )
        Spacer(Modifier.width(35.dp))
        val typeHard = "경질캡슐"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onTypeSelected(typeHard) },
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_hard",
            alpha = if (selectedType == typeHard) 1f else 0.5f
        )
        Spacer(Modifier.width(35.dp))
        val typeSoft = "연질캡슐"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onTypeSelected(typeSoft) },
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_soft",
            alpha = if (selectedType == typeSoft) 1f else 0.5f
        )
    }
}

@Composable
//모양 선택칸
fun Keyword_shape(
    selectedShape: String, // 현재 선택된 상태를 받음
    onShapeSelected: (String) -> Unit, // 선택 시 콜백
    modifier: Modifier = Modifier
){
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
        val shapecircle = "원형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapecircle) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapecircle) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapeoval = "타원형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapeoval) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapeoval) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shaperound_rectangle = "장방형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shaperound_rectangle) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shaperound_rectangle) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapehalf_circle = "반원형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapehalf_circle) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapehalf_circle) 1f else 0.5f
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        val shapethree = "삼각형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapethree) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapethree) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapefore = "사각형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapefore) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapefore) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapetilt_rectangle = "마름모"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapetilt_rectangle) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapetilt_rectangle) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapefive = "오각형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapefive) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapefive) 1f else 0.5f
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        val shapesix = "육각형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapesix) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapesix) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapeeight = "팔각형"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onShapeSelected(shapeeight) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedShape == shapeeight) 1f else 0.5f
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
fun Keyword_color(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
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
        val colorwhite = "하양"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onColorSelected(colorwhite) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedColor == colorwhite) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val coloryellow = "노랑"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onColorSelected(coloryellow) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedColor == coloryellow) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val shapetilt_rectangle = "주황"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onColorSelected(shapetilt_rectangle) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedColor == shapetilt_rectangle) 1f else 0.5f
        )
        Spacer(Modifier.width(8.dp))
        val colorpink = "분홍"
        Image(
            modifier = modifier
                .size(90.dp)
                .clickable { onColorSelected(colorpink) },
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_type_tablet",
            alpha = if (selectedColor == colorpink) 1f else 0.5f
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
fun Decide_reset(
    onDecide: () -> Unit, // 결정 콜백 추가
    onReset: () -> Unit
) {

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
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .clickable { onDecide() }, // 💡 최종 결정 로직 연결
                painter =  painterResource(R.drawable.rectangle_finish),
                contentDescription = "결정 버튼"
            )

            // 두 번째 타원형 버튼
            Image(
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .clickable { onReset() }, // 💡 초기화 로직 연결
                painter =  painterResource(R.drawable.rectangle_reset),
                contentDescription = "초기화 버튼"
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