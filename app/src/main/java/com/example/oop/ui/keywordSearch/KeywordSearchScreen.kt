package com.example.oop.ui.keywordSearch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.example.oop.ui.searchResult.SearchResultScreen

data class DetailResult(
    val letter: String = "",
    val type: String = "",
    val shape: String = "",
    val color: String = ""
)

@Composable
fun KeywordSearchScreen1(modifier: Modifier = Modifier) {
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var selectedDetails by remember { mutableStateOf(DetailResult()) }
    var finalResultList by remember { mutableStateOf<List<DetailResult>>(emptyList()) }
    var showSearchScreen by remember { mutableStateOf(false) }

    val performKeywordSearch: () -> Unit = {
        finalResultList = listOf(selectedDetails)

        println("--- 키워드 검색 결정 완료 ---")
        println("최종 결합된 DetailResult: ${selectedDetails}")
        println("최종 반환 리스트: $finalResultList")

        // 검색 결과 화면으로 임시 전환 (전환 시 이 리스트를 SearchResultScreen에 전달해야 함)
        showSearchResultScreen = true
    }

    val resetKeywordSearch: () -> Unit = {
        selectedDetails = DetailResult()
        finalResultList = emptyList()
        println("--- 키워드 검색 초기화 완료 ---")
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
                searchKeyword = "키워드검색",
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

                    item { Keyword_type(
                        selectedType = selectedDetails.type,
                        onTypeSelected = { type ->
                            selectedDetails = selectedDetails.copy(type = type) // type만 업데이트
                        }
                    ) }

                    item { Keyword_shape(
                        selectedShape = selectedDetails.shape,
                        onShapeSelected = { shape ->
                            selectedDetails = selectedDetails.copy(shape = shape) // shape만 업데이트
                        }
                    ) }

                    item { Keyword_color(
                        selectedColor = selectedDetails.color,
                        onColorSelected = { color ->
                            selectedDetails = selectedDetails.copy(color = color) // color만 업데이트
                        }
                    ) }

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
fun KeywordButton(
    selected: Boolean,
    selectedImage: Int,
    defaultImage: Int,
    contentDescription: String,
    size: androidx.compose.ui.unit.Dp = 90.dp,
    onClick: () -> Unit
) {
    Image(
        modifier = Modifier
            .size(size)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .alpha(if (selected) 1f else 0.5f),
        painter = painterResource(
            if (selected) selectedImage else defaultImage
        ),
        contentDescription = contentDescription
    )
}
@Composable
fun Keyword_type(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
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
        // 1. 정제
        val typeTablet = "정제"
        val isSelectedTablet = selectedType == typeTablet
        KeywordButton(
            selected = isSelectedTablet,
            selectedImage = R.drawable.circle_type_selected, // 선택된 이미지 리소스 ID 필요
            defaultImage = R.drawable.circle_type,
            contentDescription = "keyword_type_tablet",
            onClick = {
                onTypeSelected(if (isSelectedTablet) "" else typeTablet)
            }
        )
        Spacer(Modifier.width(35.dp))

        // 2. 경질캡슐
        val typeHard = "경질캡슐"
        val isSelectedHard = selectedType == typeHard
        KeywordButton(
            selected = isSelectedHard,
            selectedImage = R.drawable.circle_type_selected,
            defaultImage = R.drawable.circle_type,
            contentDescription = "keyword_type_hard",
            onClick = {
                onTypeSelected(if (isSelectedHard) "" else typeHard)
            }
        )
        Spacer(Modifier.width(35.dp))

        // 3. 연질캡슐
        val typeSoft = "연질캡슐"
        val isSelectedSoft = selectedType == typeSoft
        KeywordButton(
            selected = isSelectedSoft,
            selectedImage = R.drawable.circle_type_selected,
            defaultImage = R.drawable.circle_type,
            contentDescription = "keyword_type_soft",
            onClick = {
                onTypeSelected(if (isSelectedSoft) "" else typeSoft)
            }
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
        // 1. 원형
        val shapecircle = "원형"
        val isSelectedCircle = selectedShape == shapecircle
        KeywordButton(
            selected = isSelectedCircle,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_circle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedCircle) "" else shapecircle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 2. 타원형
        val shapeoval = "타원형"
        val isSelectedOval = selectedShape == shapeoval
        KeywordButton(
            selected = isSelectedOval,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_oval",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedOval) "" else shapeoval)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 3. 장방형
        val shaperound_rectangle = "장방형"
        val isSelectedRoundRectangle = selectedShape == shaperound_rectangle
        KeywordButton(
            selected = isSelectedRoundRectangle,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_round_rectangle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedRoundRectangle) "" else shaperound_rectangle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 4. 반원형
        val shapehalf_circle = "반원형"
        val isSelectedHalfCircle = selectedShape == shapehalf_circle
        KeywordButton(
            selected = isSelectedHalfCircle,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_half_rectangle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedHalfCircle) "" else shapehalf_circle)
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        // 5. 삼각형
        val shapethree = "삼각형"
        val isSelectedThree = selectedShape == shapethree
        KeywordButton(
            selected = isSelectedThree,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_three",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedThree) "" else shapethree)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 6. 사각형
        val shapefour = "사각형"
        val isSelectedFour = selectedShape == shapefour
        KeywordButton(
            selected = isSelectedFour,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_four",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedFour) "" else shapefour)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 7. 마름모형
        val shapetilt_rectangle = "마름모형"
        val isSelectedTiltRectangle = selectedShape == shapetilt_rectangle
        KeywordButton(
            selected = isSelectedTiltRectangle,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_tilt",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedTiltRectangle) "" else shapetilt_rectangle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 8. 오각형
        val shapefive = "오각형"
        val isSelectedFive = selectedShape == shapefive
        KeywordButton(
            selected = isSelectedFive,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_five",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedFive) "" else shapefive)
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        // 9. 육각형
        val shapesix = "육각형"
        val isSelectedSix = selectedShape == shapesix
        KeywordButton(
            selected = isSelectedSix,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_six",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedSix) "" else shapesix)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 10. 팔각형
        val shapeeight = "팔각형"
        val isSelectedEight = selectedShape == shapeeight
        KeywordButton(
            selected = isSelectedEight,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_shape_eight",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedEight) "" else shapeeight)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 빈 자리 1
        Image(
            modifier = Modifier.size(90.dp).alpha(0f),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "placeholder"
        )
        Spacer(Modifier.width(8.dp))

        // 빈 자리 2
        Image(
            modifier = Modifier.size(90.dp).alpha(0f),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "placeholder"
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
        // 1. 하양
        val colorwhite = "하양"
        val isSelectedWhite = selectedColor == colorwhite
        KeywordButton(
            selected = isSelectedWhite,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_white",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedWhite) "" else colorwhite)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 2. 노랑
        val coloryellow = "노랑"
        val isSelectedYellow = selectedColor == coloryellow
        KeywordButton(
            selected = isSelectedYellow,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_yellow",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedYellow) "" else coloryellow)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 3. 주황
        val colororange = "주황"
        val isSelectedOrange = selectedColor == colororange
        KeywordButton(
            selected = isSelectedOrange,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_orange",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedOrange) "" else colororange)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 4. 분홍
        val colorpink = "분홍"
        val isSelectedPink = selectedColor == colorpink
        KeywordButton(
            selected = isSelectedPink,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_pink",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedPink) "" else colorpink)
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        // 5. 빨강
        val colorred = "빨강"
        val isSelectedRed = selectedColor == colorred
        KeywordButton(
            selected = isSelectedRed,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_red",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedRed) "" else colorred)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 6. 갈색
        val colorbrown = "갈색"
        val isSelectedBrown = selectedColor == colorbrown
        KeywordButton(
            selected = isSelectedBrown,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_brown",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBrown) "" else colorbrown)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 7. 연두
        val colorbright_green = "연두"
        val isSelectedBrightGreen = selectedColor == colorbright_green
        KeywordButton(
            selected = isSelectedBrightGreen,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_bright_green",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBrightGreen) "" else colorbright_green)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 8. 초록
        val colorgreen = "초록"
        val isSelectedGreen = selectedColor == colorgreen
        KeywordButton(
            selected = isSelectedGreen,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_green",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedGreen) "" else colorgreen)
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        // 9. 청록
        val colorbluegreen = "청록"
        val isSelectedBlueGreen = selectedColor == colorbluegreen
        KeywordButton(
            selected = isSelectedBlueGreen,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_bluegreen",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBlueGreen) "" else colorbluegreen)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 10. 파랑
        val colorblue = "파랑"
        val isSelectedBlue = selectedColor == colorblue
        KeywordButton(
            selected = isSelectedBlue,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_blue",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBlue) "" else colorblue)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 11. 남색
        val colordarkblue = "남색"
        val isSelectedDarkBlue = selectedColor == colordarkblue
        KeywordButton(
            selected = isSelectedDarkBlue,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_dark_blue",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedDarkBlue) "" else colordarkblue)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 12. 회색
        val colorgray = "회색"
        val isSelectedGray = selectedColor == colorgray
        KeywordButton(
            selected = isSelectedGray,
            selectedImage = R.drawable.rectangle_shape_selected,
            defaultImage = R.drawable.rectangle_shape,
            contentDescription = "keyword_color_gray",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedGray) "" else colorgray)
            }
        )
    }
}

@Composable
fun Decide_reset(
    onDecide: () -> Unit, // 결정 콜백 추가
    onReset: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(100.dp) // 전체 컨테이너의 높이를 이미지에 맞게 설정
    ) {

        Image(
            painter = painterResource(R.drawable.keyword_underbar),
            contentDescription = "배경 직사각형 이미지",
            contentScale = ContentScale.FillBounds, // Box 크기에 맞게 이미지 늘리기
            modifier = Modifier.matchParentSize() // 부모 Box의 크기(150dp)를 따름
        )

        Row(
            modifier = Modifier
                .fillMaxSize() // 이미지와 같은 크기
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .clickable { onDecide() },
                painter =  painterResource(R.drawable.rectangle_finish),
                contentDescription = "결정 버튼"
            )

            Image(
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .clickable { onReset() },
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