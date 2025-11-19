package com.example.oop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.ui.theme.OOPTheme


@Composable//검색의 종류를 고르는 ui
fun Search_select(modifier : Modifier = Modifier) {
    Row(
        modifier = modifier.padding(40.dp).offset(x = 30.dp)
    ) {
        Text(
            text = "제품명 검색",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(Modifier.width(35.dp))
        Image(
            painter = painterResource(R.drawable.line_select),
            contentDescription = "select bar",
            modifier = Modifier.size(40.dp).offset(y = (-5).dp)
        )
        Spacer(Modifier.width(35.dp))
        Text(
            text = "키워드 검색"
        )
    }
}

@Composable//검색 바 ui
fun Search_bar(modifier : Modifier = Modifier){
    Column(
        modifier = modifier.padding(40.dp)
    ) {
        Row{
            Text(
                text = "제품명을 입력해주세요",
                modifier = Modifier.offset(x = 5.dp, y = 115.dp)
            )
            Image(
                painter = painterResource(R.drawable.search_mark),
                contentDescription = "search mark",
                modifier = Modifier.size(30.dp).offset(x = 130.dp, y = 110.dp)
            )
        }
        Canvas(modifier = Modifier.size(width = 350.dp, height = 250.dp)) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 4f
            )
        }
    }
}

@Composable//최근검색어 글씨와 비우기 기능
fun Search_delete(modifier : Modifier = Modifier){
    Row{
        Text(
            text = "최근검색어",
            modifier = Modifier.offset(x = 40.dp, y = 250.dp),
            fontSize = 13.sp
        )
        Spacer(Modifier.width(270.dp))
        Text(
            text = "비우기",
            modifier = Modifier.offset(y = 250.dp),
            fontSize = 13.sp
        )
    }
}

@Composable// 검색 내역
fun Search_history(modifier : Modifier = Modifier){
    Column{
        Text(
            text = "history1",
            modifier = Modifier.offset(x = 40.dp,y = 300.dp),
            fontSize = 13.sp
        )
        Text(
            text = "history2",
            modifier = Modifier.offset(x = 40.dp,y = 320.dp),
            fontSize = 13.sp
        )
    }
}