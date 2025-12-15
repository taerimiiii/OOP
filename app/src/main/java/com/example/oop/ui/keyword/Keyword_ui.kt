package com.example.oop.ui.keyword

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import com.example.oop.ui.Search.SearchScreen


@Composable//검색의 종류를 고르는 ui
fun Search_select2(modifier : Modifier = Modifier) {
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
        Canvas(
            modifier = Modifier.size(width = 350.dp, height = 250.dp)
                .offset(x = 28.dp, y = (-10).dp)
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 3f
            )
        }
    }
}











@Preview(showBackground = true)
@Composable
fun SearchSelectPreview() {
    Search_select2()
}