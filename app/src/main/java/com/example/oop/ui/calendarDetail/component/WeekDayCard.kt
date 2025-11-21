package com.example.oop.ui.calendarDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

data class DayUiModel(
    val date: LocalDate,
    val dayText: String,
    val weekDayText: String,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean,
)

@Composable
fun WeekDayCard(
    model: DayUiModel,
    modifier: Modifier = Modifier.Companion,
    onClick: (LocalDate) -> Unit = {},
) {
    val greenColor = Color(0xFF71E000)
    val lightGreenColor = Color(0xFFE1EFC7)

    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenWidth = remember(windowInfo.containerSize.width, density) {
        with(density) { windowInfo.containerSize.width.toDp() }
    }

    Box(
        modifier = modifier
            .width(screenWidth / 7)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = if (model.isCurrentMonth) lightGreenColor else Color.Companion.Transparent)
            .border(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                width = if (model.isSelected) 2.dp else 0.dp,
                color = if (model.isSelected) greenColor else Color.Companion.Transparent,
            )
            .wrapContentHeight()
            .clickable(enabled = model.isCurrentMonth) { onClick(model.date) }
            .alpha(if (model.isCurrentMonth) 1f else 0f),
        contentAlignment = Alignment.Companion.Center,
    ) {
        if (model.isCurrentMonth) {
            Column(
                modifier = Modifier.Companion.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    text = model.dayText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Companion.Bold,
                )
                Text(
                    text = model.weekDayText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Companion.Normal,
                )
            }
        }
    }
}