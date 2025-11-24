package com.example.oop.ui.keyword

import android.os.Bundle
import android.view.Surface
import android.widget.ImageButton
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R
import com.example.oop.ui.theme.OOPTheme


@Composable//검색의 종류를 고르는 ui
fun Search_select2(modifier : Modifier = Modifier) {
    Row(
        modifier = modifier.padding(40.dp).offset(x = 30.dp)
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
    Canvas(modifier = Modifier.size(width = 350.dp, height = 250.dp).offset(x = 28.dp, y= (-10).dp)) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 3f
        )
    }
}

@Composable
//각인 입력칸
fun Keyword_letter(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.offset( 25.dp, 140.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            append("앞면이나 뒷면의 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("각인")
            }
            append("을 입력해주세요")
        },
        maxLines = 1
    )
    Image(
        modifier = Modifier.size(370.dp).offset( 20.dp, 20.dp),
        painter =  painterResource(R.drawable.rectangle_search),
        contentDescription = "keyword_search_bar"
    )
}

@Composable
//제형 선택칸
fun Keyword_type(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.offset( 25.dp, 250.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("제형")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 37.dp, 290.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_tablet"
        )
        Spacer(Modifier.width(35.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 37.dp, 290.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_hard"
        )
        Spacer(Modifier.width(35.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 37.dp, 290.dp),
            painter =  painterResource(R.drawable.circle_type),
            contentDescription = "keyword_type_soft"
        )
    }
}

@Composable
//모양 선택칸
fun Keyword_shape(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.offset( 25.dp, 400.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("모양")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 450.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_circle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 450.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_oval"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 450.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 450.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_circle"
        )
    }
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 550.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_try"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 550.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 550.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_tilt_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 550.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_penta"
        )
    }
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 650.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_hexa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 650.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_octa"
        )
    }
}

@Composable
//색상 선택칸
fun Keyword_color(modifier: Modifier = Modifier){
    Text(
        modifier = Modifier.offset( 25.dp, 750.dp).fillMaxWidth(),
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("색상")
            }
            append("을 선택해주세요")
        },
        maxLines = 1
    )
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 800.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_circle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 800.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_oval"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 800.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 800.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_circle"
        )
    }
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 900.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_try"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 900.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 900.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_round_tilt_rectangle"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 900.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_half_penta"
        )
    }
    Row{
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 1000.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_hexa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 1000.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_octa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 1000.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_hexa"
        )
        Spacer(Modifier.width(10.dp))
        Image(
            modifier = Modifier.size(90.dp).offset( 13.dp, 1000.dp),
            painter =  painterResource(R.drawable.rectangle_shape),
            contentDescription = "keyword_shape_octa"
        )
    }
}

@Composable
fun Decide_reset(modifier: Modifier = Modifier){
    Image(
        modifier = Modifier.size(450.dp).offset( y = 650.dp),
        painter = painterResource(R.drawable.keyword_underbar),
        contentDescription = "keyword_underbar"
    )
    Image(
        modifier = Modifier.size(110.dp).offset(x = 30.dp, y = 815.dp),
        painter = painterResource(R.drawable.rectangle_reset),
        contentDescription = "keyword_allreset"
    )
    Image(
        modifier = Modifier.size(200.dp).offset(x = 180.dp, y = 770.dp),
        painter = painterResource(R.drawable.rectangle_finish),
        contentDescription = "keyword_allsearch"
    )
}