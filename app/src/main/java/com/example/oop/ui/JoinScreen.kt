package com.example.oop.ui // 1. 패키지 주소 수정 (ui.theme 아님)

// 2. 옛날 'myapplication' import 삭제됨
// 같은 폴더(ui)에 있는 UserManager 등은 import 없이 자동 연결됩니다.

import android.widget.Toast
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
import androidx.compose.material3.* // 여기서 OutlinedTextFieldDefaults를 가져옵니다
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

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

            // 로고 (pill)
            Text(
                text = "pill",
                fontSize = 32.sp,
                color = PillGreen,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
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
    val checkStates = remember { mutableStateListOf(false, false, false) }
    val termData = listOf(
        Triple("이용약관 동의 (필수)", true, "약관 내용..."),
        Triple("개인정보 수집 및 이용 동의 (필수)", true, "개인정보 처리방침 내용..."),
        Triple("마케팅 정보 수신 동의 (선택)", false, "마케팅 정보 수신 동의 내용...")
    )

    val isNextEnabled = termData.indices.all { i -> !termData[i].second || checkStates[i] }
    val allChecked = checkStates.all { it }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = dialogTitle, fontWeight = FontWeight.Bold) },
            text = { Text(text = dialogContent, fontSize = 14.sp, color = Color.DarkGray) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) { Text("확인", color = PillGreen) }
            },
            containerColor = Color.White
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = 24.dp)) {
            JoinHeader(title = "", onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    append("시작을 위해서는\n")
                    withStyle(style = SpanStyle(color = PillGreen)) { append("약관 동의") }
                    append("가 필요해요.")
                },
                fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            // 전체 동의
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (allChecked) ButtonGreen.copy(alpha = 0.3f) else InputGray, RoundedCornerShape(8.dp))
                    .clickable {
                        val newState = !allChecked
                        for (i in checkStates.indices) checkStates[i] = newState
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("전체동의 (선택항목 포함)", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = allChecked,
                    onCheckedChange = {
                        val newState = !allChecked
                        for (i in checkStates.indices) checkStates[i] = newState
                    },
                    colors = CheckboxDefaults.colors(checkedColor = PillGreen)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 개별 약관
            termData.forEachIndexed { index, data ->
                val (title, _, content) = data
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.clickable { checkStates[index] = !checkStates[index] })
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { dialogTitle = title; dialogContent = content; showDialog = true }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "상세보기", tint = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Checkbox(checked = checkStates[index], onCheckedChange = { checkStates[index] = it }, colors = CheckboxDefaults.colors(checkedColor = PillGreen))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNextClick, enabled = isNextEnabled,
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen, disabledContainerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("다음", fontSize = 18.sp, color = if (isNextEnabled) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold)
            }
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
    val isPhoneValid = Regex("^010-\\d{4}-\\d{4}$").matches(phoneNumber)

    Scaffold(modifier = Modifier.fillMaxSize().imePadding(), containerColor = Color.White) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = 24.dp)) {
            JoinHeader(title = "", onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PillGreen)) { append("휴대폰 번호") }
                    append("로\n간편하게 가입해요!")
                },
                fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { if (it.length <= 13) phoneNumber = it },
                label = { Text("휴대폰 번호 입력") },
                placeholder = { Text("010-0000-0000") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                isError = phoneNumber.isNotEmpty() && !isPhoneValid,
                // [수정 핵심] TextFieldDefaults -> OutlinedTextFieldDefaults 변경
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = PillGreen,
                    unfocusedBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("번호는 010-0000-0000형태로 작성해주세요.", color = if (phoneNumber.isEmpty() || isPhoneValid) Color.Gray else Color.Red, fontSize = 12.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNextClick, enabled = isPhoneValid,
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen, disabledContainerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("인증 문자 받기", fontSize = 18.sp, color = if (isPhoneValid) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// [회원가입 2-1] 인증번호 입력
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen2_1(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    val context = LocalContext.current
    var inputCode by remember { mutableStateOf("") }
    var generatedCode by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(180) }

    fun sendVerificationCode() {
        val newCode = (100000..999999).random().toString()
        generatedCode = newCode
        timeLeft = 180
        Toast.makeText(context, "pill 회원가입 인증번호는 ${newCode} 입니다.", Toast.LENGTH_LONG).show()
    }

    LaunchedEffect(Unit) { sendVerificationCode() }
    LaunchedEffect(generatedCode) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    val isNextEnabled = (inputCode == generatedCode && timeLeft > 0)
    val timeText = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)

    Scaffold(modifier = Modifier.fillMaxSize().imePadding(), containerColor = Color.White) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = 24.dp)) {
            JoinHeader(title = "", onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PillGreen)) { append("인증번호") }
                    append("를\n입력해주세요.")
                },
                fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            // 읽기 전용 전화번호 필드
            OutlinedTextField(
                value = "010-1234-5678",
                onValueChange = {},
                readOnly = true,
                label = { Text("입력한 휴대폰 번호") },
                modifier = Modifier.fillMaxWidth(),
                // [수정] OutlinedTextFieldDefaults 사용 (배경색 설정 방식 변경)
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputGray,
                    unfocusedContainerColor = InputGray,
                    disabledContainerColor = InputGray,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 인증번호 입력 필드
            OutlinedTextField(
                value = inputCode,
                onValueChange = { if (it.length <= 6) inputCode = it },
                label = { Text("인증번호 6자리") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                trailingIcon = { Text(text = timeText, color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 12.dp)) },
                // [수정] OutlinedTextFieldDefaults 사용
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = PillGreen,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { sendVerificationCode() }) {
                    Text("인증번호 재전송", color = Color.Gray, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNextClick, enabled = isNextEnabled,
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen, disabledContainerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("다음", fontSize = 18.sp, color = if (isNextEnabled) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold)
            }
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
        modifier = Modifier.fillMaxSize().background(Color.White).systemBarsPadding().imePadding().padding(horizontal = 24.dp).verticalScroll(scrollState)
    ) {
        JoinHeader(title = "", onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = buildAnnotatedString {
                append("원활한 서비스를 위해,\n")
                withStyle(style = SpanStyle(color = PillGreen)) { append("필수 정보") }
                append("를 입력해주세요!")
            },
            fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("이메일") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("비밀번호") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(), singleLine = true)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("이름") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("다음", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// [회원가입 4] 닉네임 입력 및 저장
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen4(email: String, password: String, name: String, phoneNumber: String, onFinishClick: () -> Unit) {
    var nickname by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize().imePadding(), containerColor = Color.White) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(horizontal = 24.dp)) {
            JoinHeader(title = "", onBackClick = null)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = buildAnnotatedString {
                    append("마지막으로,\n")
                    withStyle(style = SpanStyle(color = PillGreen)) { append("닉네임") }
                    append("을 입력해주세요!")
                },
                fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("닉네임") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // [수정] OutlinedTextFieldDefaults 사용
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = PillGreen
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // 1. 회원가입 정보 묶기 (빨간줄 뜨면 UserAccount 클래스가 있는지 확인 필요)
                    // val newUser = UserAccount(email, password, name, phoneNumber, nickname)
                    // UserManager.register(newUser)
                    onFinishClick()
                },
                enabled = nickname.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen, disabledContainerColor = Color.LightGray),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("회원가입 완료", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}