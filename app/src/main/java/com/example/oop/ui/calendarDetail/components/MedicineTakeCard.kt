package com.example.oop.ui.calendarDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.oop.data.model.Medicine

@Composable
fun MedicineTakeCard(
    medicine: Medicine?,
    isTaken: Boolean,
    //errorMessage: String?,
    onTakenChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // 나중에 색 테마로 쓰기
    val redColor = Color(0xFFD21818)
    val greenColor = Color(0xFF38B000)
    val lightGrayColor = Color(0xFFEAEAEA)
    val backGreenColor = Color(0xFFDDE8E4)
    val blackColor = Color(0xFF000000)
    val grayTextColor = Color(0xFF666666)

    Column(
        modifier = modifier
            .fillMaxWidth(0.96f)
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onTakenChanged(!isTaken) } // 클릭
    ) {
        // 상단 회색 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(lightGrayColor)
                .padding(16.dp)
        ) {
            if (medicine != null) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Gap
                ) {
                    // 왼쪽 이미지
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(backGreenColor),
                        contentAlignment = Alignment.Center, // 중앙
                    ) {
                        if (!medicine.itemImage.isNullOrEmpty()) {
                            AsyncImage(
                                model = medicine.itemImage,
                                contentDescription = "의약품 이미지",
                                modifier = Modifier
                            )
                        }
                    }

                    // 오른쪽 텍스트 정보
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                    ) {
                        // ITEM_NAME ex)가스디알정
                        Text(
                            text = medicine.itemName ?: "ITEM_NAME",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = blackColor
                        )

                        // ENTP_NAME(회사명) ex)일동제약
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

                        // CHART(외형) ex) 녹색의 원형 필름코팅정
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

                        // CLASS_NAME(분류명) ex) 항악성종양제
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


        // 하단 색상 영역 (초록/빨강)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    color = if (isTaken) {
                        greenColor
                    } else {
                        redColor
                    },
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

