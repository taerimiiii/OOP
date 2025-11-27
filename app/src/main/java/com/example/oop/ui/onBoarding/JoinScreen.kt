package com.example.oop.ui.onBoarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R



// --- 공통 색상 및 스타일 정의 ---
val PillGreen = Color(0xFF8BC34A)       // 로고 및 강조 텍스트 색상
val ButtonGreen = Color(0xFFC0F56F)     // 버튼 배경 색상
val InputGray = Color(0xFFF0F0F0)       // 입력창 배경 색상

// --- 공통 헤더 컴포넌트 ---
@Composable
fun JoinHeader(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 뒤로가기 버튼
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            // 로고 이미지 (오류 방지를 위해 기본 아이콘 사용)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(PillGreen)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        // 화면 타이틀 (예: 회원가입1)
        // 디자인상 타이틀이 없는 경우도 있어 빈 문자열이면 공간만 차지하게 처리
        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}

// ==========================================
// [회원가입 1] 약관 동의 화면
// ==========================================
@Composable
fun JoinScreen1(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        JoinHeader(title = "회원가입1", onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                append("시작을 위해서는\n")
                withStyle(style = SpanStyle(color = PillGreen)) {
                    append("약관 동의")
                }
                append("가 필요해요.")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 전체 동의
        var allChecked by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (allChecked) ButtonGreen.copy(alpha = 0.3f) else InputGray, RoundedCornerShape(8.dp))
                .clickable { allChecked = !allChecked }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("전체동의 (선택항목 포함)", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = allChecked,
                onCheckedChange = { allChecked = it },
                colors = CheckboxDefaults.colors(checkedColor = PillGreen)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 약관 리스트 (단순 UI용)
        val terms = listOf("이용약관 동의 (필수)", "개인정보 수집 및 이용 동의 (필수)", "마케팅 정보 수신 동의 (선택)")
        terms.forEach { term ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = term, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "상세보기",
                    tint = Color.Gray
                )
                Checkbox(
                    checked = allChecked, // 간단하게 전체동의 상태를 따라가도록 설정
                    onCheckedChange = { },
                    colors = CheckboxDefaults.colors(checkedColor = PillGreen)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("다음", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// [회원가입 2] 휴대폰 번호 입력
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen2(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        JoinHeader(title = "회원가입2", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = PillGreen)) {
                    append("휴대폰 번호")
                }
                append("로\n간편하게 가입해요!")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("원활한 서비스를 위해, 휴대폰 번호를 입력해주세요.", color = Color.Gray, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("휴대폰 번호 입력") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PillGreen,
                unfocusedBorderColor = Color.LightGray,
                disabledBorderColor = Color.LightGray,
                errorBorderColor = Color.Red
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("인증 문자 받기", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// [회원가입 2-1] 인증번호 입력
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen2_1(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    var authCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        JoinHeader(title = "회원가입2-1", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = PillGreen)) {
                    append("인증번호")
                }
                append("를\n입력해주세요.")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = "010-1234-5678",
            onValueChange = {},
            readOnly = true,
            label = { Text("입력한 휴대폰 번호") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = authCode,
            onValueChange = { authCode = it },
            label = { Text("인증번호 6자리") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PillGreen,
                unfocusedBorderColor = Color.LightGray,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("다음", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// [회원가입 3] 필수 정보 입력
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen3(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState) // 내용이 길어질 수 있어 스크롤 추가
    ) {
        JoinHeader(title = "회원가입3", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                append("원활한 서비스를 위해,\n")
                withStyle(style = SpanStyle(color = PillGreen)) {
                    append("필수 정보")
                }
                append("를 입력해주세요!")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 이메일
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 비밀번호
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 이름
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("다음", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// [회원가입 4] 닉네임 입력 (마지막)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen4(onFinishClick: () -> Unit) {
    var nickname by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        // 마지막 화면은 뒤로가기 없이 로고만 우측 상단 등 배치하거나 헤더 없이 처리
        // 여기선 디자인 일관성을 위해 헤더 사용하되 뒤로가기 null
        JoinHeader(title = "회원가입4", onBackClick = null)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = buildAnnotatedString {
                append("마지막으로,\n")
                withStyle(style = SpanStyle(color = PillGreen)) {
                    append("닉네임")
                }
                append("을 입력해주세요!")
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 닉네임 입력
        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("닉네임") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PillGreen,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinishClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray), // 완료 전엔 회색, 입력시 색상변경 로직 추가 가능
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("회원가입 완료", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}