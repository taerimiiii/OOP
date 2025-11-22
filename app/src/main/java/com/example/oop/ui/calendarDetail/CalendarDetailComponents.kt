package com.example.oop.ui.calendarDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.oop.data.api.MedicineApiDataSource
import com.example.oop.data.model.Medicine
import com.kizitonwose.calendar.compose.WeekCalendar as KizWeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.year} ${this.month.displayText(short = short)}"
}

fun Month.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}

fun DayOfWeek.displayText(short: Boolean = false): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return this.getDisplayName(style, Locale.getDefault())
}

@Composable
fun WeekCalendar(
    targetDate: LocalDate,
) {
    //val greenColor = Color(0xFF71E000)
    val whiteColor = Color(0xFFFFFFFF)
    val blackColor = Color(0xFF000000)
    //val lightGreenColor = Color(0xFFE1EFC7)

    val targetMonth = remember(targetDate) { YearMonth.from(targetDate) }
    val startDate = remember(targetMonth) { targetMonth.atDay(1) }
    val endDate = remember(targetMonth) { targetMonth.atEndOfMonth() }
    var selection by remember(targetDate) { mutableStateOf(targetDate) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(whiteColor)
            .padding(vertical = 8.dp),
    ) {
        val state = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = targetDate,
        )

        // 주간 캘린더 타이틀
        val visibleYearMonth = remember(targetMonth) { targetMonth }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 4.dp, top = 10.dp),
            text = visibleYearMonth.displayText(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = blackColor,
        )

        // 주간 캘린더 날짜 카드 영역
        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides blackColor) {
            KizWeekCalendar(
                modifier = Modifier.padding(vertical = 4.dp),
                state = state,
                calendarScrollPaged = false,
                // 타겟 날짜 기준 연월만 표기
                dayContent = { day ->
                    val isInTargetMonth = YearMonth.from(day.date) == targetMonth
                    Day(
                        date = day.date,
                        isCurrentMonth = isInTargetMonth,
                        selected = selection == day.date && isInTargetMonth,
                    ) { clickedDate ->
                        if (isInTargetMonth) {
                            selection = clickedDate
                        }
                    }
                },
            )
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
private fun Day(
    date: LocalDate,
    isCurrentMonth: Boolean,
    selected: Boolean = false,
    onClick: (LocalDate) -> Unit = {},
) {
    val greenColor = Color(0xFF71E000)
    //val whiteColor = Color(0xFFFFFFFF)
    //val blackColor = Color(0xFF000000)
    val lightGreenColor = Color(0xFFE1EFC7)

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenWidth = remember(windowInfo.containerSize.width, density) {
        with(density) { windowInfo.containerSize.width.toDp() }
    }
    Box(
        modifier = Modifier
            // If paged scrolling is disabled (calendarScrollPaged = false),
            // you must set the day width on the WeekCalendar!
            .width(screenWidth / 7)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = if (isCurrentMonth) lightGreenColor else Color.Transparent)
            .border(
                shape = RoundedCornerShape(8.dp),
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) greenColor else Color.Transparent,
            )
            .wrapContentHeight()
            .clickable(enabled = isCurrentMonth) { onClick(date) }
            .alpha(if (isCurrentMonth) 1f else 0f),
        contentAlignment = Alignment.Center,
    ) {
        if (isCurrentMonth) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = dateFormatter.format(date),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = date.dayOfWeek.displayText().take(3),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}


// 즐겨찾기 의약품 컴포넌트
// 회원 정보 DB에 즐겨찾기 의약품 등록해둔 item_seq(품목일련번호)로 API에서 정보 불러오기.
// 근데 이게 날짜마다 복용/미복용 상태를 관리해야 함.
@Composable
fun MedicineTakeCard(
    itemSeq: String,
    modifier: Modifier = Modifier
) {
    var isTaken by remember { mutableStateOf(false) } // false = 빨강, true = 초록
    var medicine by remember { mutableStateOf<Medicine?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showImageDialog by remember { mutableStateOf(false) }
    
    val apiDataSource = remember { MedicineApiDataSource() }
    
    // API 호출
    LaunchedEffect(itemSeq) {
        isLoading = true
        errorMessage = null
        
        apiDataSource.getMedicines(
            pageNo = 1,
            numOfRows = 200,
            type = "json",
            itemSeq = itemSeq
        ).onSuccess { medicines ->
            medicine = medicines.firstOrNull()
            isLoading = false
        }.onFailure { error ->
            errorMessage = error.message
            isLoading = false
        }
    }
    
    val redColor = Color(0xFFD21818)
    val greenColor = Color(0xFF38B000)
    val lightGrayColor = Color(0xFFE5E5E5)
    val darkGrayColor = Color(0xFFCCCCCC)
    val blackColor = Color(0xFF000000)
    val grayTextColor = Color(0xFF666666)
    
    Column(
        modifier = modifier
            .fillMaxWidth(0.96f)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { isTaken = !isTaken }
    ) {
        // 상단 정보 영역 (회색 배경)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(lightGrayColor)
                .padding(16.dp)
        ) {
            if (isLoading) {
                // 로딩 중
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "로딩 중...",
                        fontSize = 14.sp,
                        color = grayTextColor
                    )
                }
            } else if (errorMessage != null) {
                // 에러 발생
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "오류: $errorMessage",
                        fontSize = 14.sp,
                        color = redColor
                    )
                }
            } else if (medicine == null) {
                // 데이터 없음
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "의약품 정보를 찾을 수 없습니다",
                        fontSize = 14.sp,
                        color = grayTextColor
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 왼쪽 이미지 공간 (이미지의 왼쪽 절반만 표시)
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(darkGrayColor), //lightGrayColor
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!medicine?.itemImage.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .width(80.dp) // 원본 이미지의 2배 크기
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = medicine?.itemImage,
                                    contentDescription = "의약품 이미지",
                                    modifier = Modifier
                                        .width(80.dp)
                                        .fillMaxHeight(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    // 오른쪽 텍스트 정보
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        // ITEM_NAME
                        Text(
                            text = medicine?.itemName ?: "ITEM_NAME",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = blackColor
                        )

                        // 회사명 ENTP_NAME
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "회사명",
                                fontSize = 14.sp,
                                color = blackColor,
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = medicine?.entpName ?: "ENTP_NAME",
                                fontSize = 14.sp,
                                color = grayTextColor
                            )
                        }

                        // 외형 CHART
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "외형",
                                fontSize = 14.sp,
                                color = blackColor,
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = medicine?.chart ?: "CHART",
                                fontSize = 14.sp,
                                color = grayTextColor
                            )
                        }

                        // 분류명 CLASS_NAME
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "분류명",
                                fontSize = 14.sp,
                                color = blackColor,
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = medicine?.className ?: "CLASS_NAME",
                                fontSize = 14.sp,
                                color = grayTextColor
                            )
                        }
                    }
                }
            }
        }
        
        // 하단 색상 바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = if (isTaken) greenColor else redColor,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp
                    )
                )
        )
    }
}

@Preview
@Composable
private fun Example7Preview() {
    WeekCalendar(targetDate = LocalDate.now())
}