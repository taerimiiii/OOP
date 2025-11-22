package com.example.oop.ui.calendarDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.oop.data.model.Medicine

@Composable
fun MedicineTakeCard(
    medicine: Medicine?,
    isTaken: Boolean,
    isLoading: Boolean,
    errorMessage: String?,
    onTakenChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
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
            .clickable { onTakenChanged(!isTaken) }
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
                            .background(darkGrayColor),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!medicine.itemImage.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                AsyncImage(
                                    model = medicine.itemImage,
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
                            text = medicine.itemName,
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
                                text = medicine.entpName ?: "ENTP_NAME",
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
                                text = medicine.chart ?: "CHART",
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
                                text = medicine.className ?: "CLASS_NAME",
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

