package com.example.oop.ui.keyword

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R


@Composable//검색의 종류를 고르는 ui
fun Search_select2(modifier : Modifier = Modifier) {
        Row(
        ) {
            Text(
                text = "제품명 검색"
            )
            Spacer(Modifier.width(35.dp))
            Image(
                painter = painterResource(R.drawable.line_select),
                contentDescription = "select bar",
                modifier = Modifier.size(40.dp).offset(y = (-5).dp)
            )
            Spacer(Modifier.width(35.dp))
            Text(
                text = "키워드 검색",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 3f
            )
        }
    }



@Composable
}