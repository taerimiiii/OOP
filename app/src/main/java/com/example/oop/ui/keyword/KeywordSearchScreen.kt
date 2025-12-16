package com.example.oop.ui.keyword

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
import com.example.oop.ui.view.SearchResultScreen

data class DetailResult(
    val letter: String? = null,
    val type: String? = null,
    val shape: String? = null,
    val color: String? = null
)

@Composable
fun KeywordSearchScreen1(modifier: Modifier = Modifier) {
    var showDetailScreen by remember { mutableStateOf(false) }
    var showSearchResultScreen by remember { mutableStateOf(false) }
    var selectedDetails by remember { mutableStateOf(DetailResult()) }
    var finalResultList by remember { mutableStateOf<List<DetailResult>>(emptyList()) }
    var showSearchScreen by remember { mutableStateOf(false) }

    val performKeywordSearch: () -> Unit = {
        val hasSelection = selectedDetails.letter?.isNotBlank() == true ||
                selectedDetails.type != null ||
                selectedDetails.shape != null ||
                selectedDetails.color != null

        if (hasSelection) {
            finalResultList = listOf(selectedDetails)

            println("--- 키워드 검색 결정 완료 ---")
            println("최종 결합된 DetailResult: ${selectedDetails}")
            println("최종 반환 리스트: $finalResultList")

            // 검색 결과 화면으로 전환
            showSearchResultScreen = true
        } else {
            println("--- 키워드 검색 취소: 검색 조건이 없습니다. ---")
        }
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
                searchKeyword = "키워드검색: $selectedDetails",
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
                        // 4. letter는 Nullable이므로 Elvis 연산자 ?: ""를 사용하여 빈 문자열로 대체
                        initialSearchText = selectedDetails.letter ?: "",
                        onSearch = { letter ->
                            // 입력된 문자열이 비어있지 않으면 letter로, 비어있으면 null로 업데이트
                            selectedDetails = selectedDetails.copy(letter = letter.ifBlank { null })
                        }
                    ) }

                    item { Keyword_type(
                        selectedType = selectedDetails.type,
                        onTypeSelected = { type ->
                            selectedDetails = selectedDetails.copy(type = type)
                        }
                    ) }

                    item { Keyword_shape(
                        selectedShape = selectedDetails.shape,
                        onShapeSelected = { shape ->
                            selectedDetails = selectedDetails.copy(shape = shape)
                        }
                    ) }

                    item { Keyword_color(
                        selectedColor = selectedDetails.color,
                        onColorSelected = { color ->
                            selectedDetails = selectedDetails.copy(color = color)
                        }
                    ) }

                    item { Decide_reset(
                        onDecide = performKeywordSearch,
                        onReset = resetKeywordSearch
                    ) }
                }
            }
        }
    }
}

@Composable
//각인 입력칸
fun Keyword_letter(
    initialSearchText: String,
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
            onSearch(it)
        },
        label = { Text("각인을 입력하세요") },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = {
                    searchText = ""
                    onSearch("") // 초기화 시 외부 상태에도 빈 문자열 전달 -> null로 처리됨
                }) {
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
    selectedType: String?,
    onTypeSelected: (String?) -> Unit,
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
            selectedImage = R.drawable.circle_type_selected_tablet,
            defaultImage = R.drawable.circle_type_tablet,
            contentDescription = "keyword_type_tablet",
            onClick = {
                onTypeSelected(if (isSelectedTablet) null else typeTablet)
            }
        )
        Spacer(Modifier.width(35.dp))

        // 2. 경질캡슐
        val typeHard = "경질캡슐"
        val isSelectedHard = selectedType == typeHard
        KeywordButton(
            selected = isSelectedHard,
            selectedImage = R.drawable.circle_type_selected_hard,
            defaultImage = R.drawable.circle_type_hard,
            contentDescription = "keyword_type_hard",
            onClick = {
                onTypeSelected(if (isSelectedHard) null else typeHard)
            }
        )
        Spacer(Modifier.width(35.dp))

        // 3. 연질캡슐
        val typeSoft = "연질캡슐"
        val isSelectedSoft = selectedType == typeSoft
        KeywordButton(
            selected = isSelectedSoft,
            selectedImage = R.drawable.circle_type_selected_soft,
            defaultImage = R.drawable.circle_type_soft,
            contentDescription = "keyword_type_soft",
            onClick = {
                onTypeSelected(if (isSelectedSoft) null else typeSoft)
            }
        )
    }
}

@Composable
//모양 선택칸
fun Keyword_shape(
    selectedShape: String?,
    onShapeSelected: (String?) -> Unit,
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
            selectedImage = R.drawable.rectangle_shape_selected_circle,
            defaultImage = R.drawable.rectangle_shape_circle,
            contentDescription = "keyword_shape_circle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedCircle) null else shapecircle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 2. 타원형
        val shapeoval = "타원형"
        val isSelectedOval = selectedShape == shapeoval
        KeywordButton(
            selected = isSelectedOval,
            selectedImage = R.drawable.rectangle_shape_selected_oval,
            defaultImage = R.drawable.rectangle_shape_oval,
            contentDescription = "keyword_shape_oval",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedOval) null else shapeoval)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 3. 장방형
        val shaperound_rectangle = "장방형"
        val isSelectedRoundRectangle = selectedShape == shaperound_rectangle
        KeywordButton(
            selected = isSelectedRoundRectangle,
            selectedImage = R.drawable.rectangle_shape_selected_round,
            defaultImage = R.drawable.rectangle_shape_round,
            contentDescription = "keyword_shape_round_rectangle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedRoundRectangle) null else shaperound_rectangle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 4. 반원형
        val shapehalf_circle = "반원형"
        val isSelectedHalfCircle = selectedShape == shapehalf_circle
        KeywordButton(
            selected = isSelectedHalfCircle,
            selectedImage = R.drawable.rectangle_shape_selected_half,
            defaultImage = R.drawable.rectangle_shape_half,
            contentDescription = "keyword_shape_half_rectangle",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedHalfCircle) null else shapehalf_circle)
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
            selectedImage = R.drawable.rectangle_shape_selected_three,
            defaultImage = R.drawable.rectangle_shape_three,
            contentDescription = "keyword_shape_three",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedThree) null else shapethree)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 6. 사각형
        val shapefour = "사각형"
        val isSelectedFour = selectedShape == shapefour
        KeywordButton(
            selected = isSelectedFour,
            selectedImage = R.drawable.rectangle_shape_selected_four,
            defaultImage = R.drawable.rectangle_shape_four,
            contentDescription = "keyword_shape_four",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedFour) null else shapefour)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 7. 마름모형
        val shapetilt_rectangle = "마름모형"
        val isSelectedTiltRectangle = selectedShape == shapetilt_rectangle
        KeywordButton(
            selected = isSelectedTiltRectangle,
            selectedImage = R.drawable.rectangle_shape_selected_tilt,
            defaultImage = R.drawable.rectangle_shape_tilt,
            contentDescription = "keyword_shape_tilt",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedTiltRectangle) null else shapetilt_rectangle)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 8. 오각형
        val shapefive = "오각형"
        val isSelectedFive = selectedShape == shapefive
        KeywordButton(
            selected = isSelectedFive,
            selectedImage = R.drawable.rectangle_shape_selected_five,
            defaultImage = R.drawable.rectangle_shape_five,
            contentDescription = "keyword_shape_five",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedFive) null else shapefive)
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
            selectedImage = R.drawable.rectangle_shape_selected_six,
            defaultImage = R.drawable.rectangle_shape_six,
            contentDescription = "keyword_shape_six",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedSix) null else shapesix)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 10. 팔각형
        val shapeeight = "팔각형"
        val isSelectedEight = selectedShape == shapeeight
        KeywordButton(
            selected = isSelectedEight,
            selectedImage = R.drawable.rectangle_shape_selected_eight,
            defaultImage = R.drawable.rectangle_shape_eight,
            contentDescription = "keyword_shape_eight",
            size = 90.dp,
            onClick = {
                onShapeSelected(if (isSelectedEight) null else shapeeight)
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
    selectedColor: String?,
    onColorSelected: (String?) -> Unit,
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
            selectedImage = R.drawable.rectangle_shape_selected_white,
            defaultImage = R.drawable.rectangle_shape_white,
            contentDescription = "keyword_color_white",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedWhite) null else colorwhite)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 2. 노랑
        val coloryellow = "노랑"
        val isSelectedYellow = selectedColor == coloryellow
        KeywordButton(
            selected = isSelectedYellow,
            selectedImage = R.drawable.rectangle_shape_selected_yellow,
            defaultImage = R.drawable.rectangle_shape_yellow,
            contentDescription = "keyword_color_yellow",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedYellow) null else coloryellow)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 3. 주황
        val colororange = "주황"
        val isSelectedOrange = selectedColor == colororange
        KeywordButton(
            selected = isSelectedOrange,
            selectedImage = R.drawable.rectangle_shape_selected_orange,
            defaultImage = R.drawable.rectangle_shape_orange,
            contentDescription = "keyword_color_orange",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedOrange) null else colororange)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 4. 분홍
        val colorpink = "분홍"
        val isSelectedPink = selectedColor == colorpink
        KeywordButton(
            selected = isSelectedPink,
            selectedImage = R.drawable.rectangle_shape_selected_pink,
            defaultImage = R.drawable.rectangle_shape_pink,
            contentDescription = "keyword_color_pink",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedPink) null else colorpink)
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
            selectedImage = R.drawable.rectangle_shape_selected_red,
            defaultImage = R.drawable.rectangle_shape_red,
            contentDescription = "keyword_color_red",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedRed) null else colorred)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 6. 갈색
        val colorbrown = "갈색"
        val isSelectedBrown = selectedColor == colorbrown
        KeywordButton(
            selected = isSelectedBrown,
            selectedImage = R.drawable.rectangle_shape_selected_brown,
            defaultImage = R.drawable.rectangle_shape_brown,
            contentDescription = "keyword_color_brown",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBrown) null else colorbrown)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 7. 연두
        val colorbright_green = "연두"
        val isSelectedBrightGreen = selectedColor == colorbright_green
        KeywordButton(
            selected = isSelectedBrightGreen,
            selectedImage = R.drawable.rectangle_shape_selected_bright_green,
            defaultImage = R.drawable.rectangle_shape_bright_green,
            contentDescription = "keyword_color_bright_green",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBrightGreen) null else colorbright_green)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 8. 초록
        val colorgreen = "초록"
        val isSelectedGreen = selectedColor == colorgreen
        KeywordButton(
            selected = isSelectedGreen,
            selectedImage = R.drawable.rectangle_shape_selected_green,
            defaultImage = R.drawable.rectangle_shape_green,
            contentDescription = "keyword_color_green",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedGreen) null else colorgreen)
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
            selectedImage = R.drawable.rectangle_shape_selected_bluegreen,
            defaultImage = R.drawable.rectangle_shape_bluegreen,
            contentDescription = "keyword_color_bluegreen",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBlueGreen) null else colorbluegreen)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 10. 파랑
        val colorblue = "파랑"
        val isSelectedBlue = selectedColor == colorblue
        KeywordButton(
            selected = isSelectedBlue,
            selectedImage = R.drawable.rectangle_shape_selected_blue,
            defaultImage = R.drawable.rectangle_shape_blue,
            contentDescription = "keyword_color_blue",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedBlue) null else colorblue)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 11. 남색
        val colordarkblue = "남색"
        val isSelectedDarkBlue = selectedColor == colordarkblue
        KeywordButton(
            selected = isSelectedDarkBlue,
            selectedImage = R.drawable.rectangle_shape_selected_darkblue,
            defaultImage = R.drawable.rectangle_shape_darkblue,
            contentDescription = "keyword_color_dark_blue",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedDarkBlue) null else colordarkblue)
            }
        )
        Spacer(Modifier.width(8.dp))

        // 12. 회색
        val colorgray = "회색"
        val isSelectedGray = selectedColor == colorgray
        KeywordButton(
            selected = isSelectedGray,
            selectedImage = R.drawable.rectangle_shape_selected_gray,
            defaultImage = R.drawable.rectangle_shape_gray,
            contentDescription = "keyword_color_gray",
            size = 90.dp,
            onClick = {
                onColorSelected(if (isSelectedGray) null else colorgray)
            }
        )
    }
}

@Composable
fun Decide_reset(
    onDecide: () -> Unit,
    onReset: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(100.dp)
    ) {

        // 1. 배경 이미지
        Image(
            painter = painterResource(R.drawable.keyword_underbar),
            contentDescription = "배경 직사각형 이미지",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .clickable { onDecide() },
                contentAlignment = Alignment.Center
            ) {
                // 결정 버튼 이미지
                Image(
                    modifier = Modifier.matchParentSize(),
                    painter = painterResource(R.drawable.rectangle_finish),
                    contentDescription = "결정 버튼 배경",
                    contentScale = ContentScale.FillBounds
                )
                // 결정 텍스트
                Text(
                    text = "검색하기",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(60.dp)
                    .clickable { onReset() },
                contentAlignment = Alignment.Center
            ) {
                // 초기화 버튼 이미지
                Image(
                    modifier = Modifier.matchParentSize(),
                    painter = painterResource(R.drawable.rectangle_reset),
                    contentDescription = "초기화 버튼 배경",
                    contentScale = ContentScale.FillBounds
                )
                // 초기화 텍스트
                Text(
                    text = "초기화",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
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