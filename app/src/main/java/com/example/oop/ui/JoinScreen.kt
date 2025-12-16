package com.example.oop.ui.theme

import com.example.myapplication.ui.theme.UserAccount
import com.example.myapplication.ui.theme.UserManager

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

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.Arrangement

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
        // 화면 타이틀
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
    // --- 상태 관리 변수들 ---
    // 각 약관의 체크 상태를 리스트로 관리 (0: 이용약관, 1: 개인정보, 2: 마케팅)
    // false 3개로 초기화
    val checkStates = remember { mutableStateListOf(false, false, false) }

    // 약관 데이터 (제목, 필수여부, 상세내용)
    val termData = listOf(
        Triple("이용약관 동의 (필수)", true, """
            제1조 (목적)
            본 약관은 회원이 서비스의 이용조건 및 절차, 회사와 회원 간의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.
            
            제2조 (용어의 정의)
            1. "서비스"라 함은 회사가 제공하는 모바일 앱 기반의 모든 서비스를 의미합니다.
            2. "회원"이라 함은 본 약관에 동의하고 가입한 자를 의미합니다.
            
            제3조 (약관의 효력)
            본 약관은 서비스를 통하여 이를 공지하거나 전자우편 등의 방법으로 회원에게 통지함으로써 효력이 발생합니다.
        """.trimIndent()),

        Triple("개인정보 수집 및 이용 동의 (필수)", true, """
            1. 수집하는 개인정보 항목
            - 필수항목: 이름, 이메일, 비밀번호, 휴대전화번호
            - 선택항목: 마케팅 수신 여부
            
            2. 개인정보의 수집 및 이용목적
            - 회원가입 및 관리
            - 서비스 제공 및 계약의 이행
            
            3. 보유 및 이용기간
            - 회원 탈퇴 시까지 (단, 관계 법령에 따름)
        """.trimIndent()),

        Triple("마케팅 정보 수신 동의 (선택)", false, """
            회사는 다음과 같은 목적으로 개인정보를 수집 및 이용합니다.
            
            1. 신규 서비스(제품) 개발 및 맞춤 서비스 제공
            2. 이벤트 및 광고성 정보 제공 및 참여 기회 제공
            
            * 귀하는 위와 같은 마케팅 목적의 개인정보 수집 및 이용에 거부할 권리가 있으며, 거부 시에도 기본 서비스 이용에는 제한이 없습니다.
        """.trimIndent())
    )

    // 파생 상태: 모든 필수 항목이 체크되었는지 확인 (버튼 활성화용)
    // termData[i].second가 true(필수)인 것들이 모두 checkStates[i]가 true인지 검사
    val isNextEnabled = termData.indices.all { i ->
        !termData[i].second || checkStates[i]
    }

    // 파생 상태: 전체가 다 체크되었는지 (전체동의 버튼용)
    val allChecked = checkStates.all { it }

    // --- 팝업(Dialog) 관련 상태 ---
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }

    // --- 팝업 UI (상세보기 눌렀을 때 뜸) ---
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = dialogTitle, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(text = dialogContent, fontSize = 14.sp, color = Color.DarkGray)
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("확인", color = PillGreen)
                }
            },
            containerColor = Color.White
        )
    }

    // --- 메인 UI ---
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            JoinHeader(title = "", onBackClick = onBackClick)

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

            // [전체 동의 버튼]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (allChecked) ButtonGreen.copy(alpha = 0.3f) else InputGray,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        // 전체 선택/해제 로직
                        val newState = !allChecked
                        for (i in checkStates.indices) {
                            checkStates[i] = newState
                        }
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
                        for (i in checkStates.indices) {
                            checkStates[i] = newState
                        }
                    },
                    colors = CheckboxDefaults.colors(checkedColor = PillGreen)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // [개별 약관 리스트]
            termData.forEachIndexed { index, data ->
                val (title, isRequired, content) = data

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. 텍스트 부분 (클릭 시 체크박스 토글)
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.clickable { checkStates[index] = !checkStates[index] }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // 2. 화살표 아이콘 (클릭 시 팝업 오픈)
                    IconButton(
                        onClick = {
                            dialogTitle = title
                            dialogContent = content
                            showDialog = true
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "상세보기",
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // 3. 체크박스
                    Checkbox(
                        checked = checkStates[index],
                        onCheckedChange = { checkStates[index] = it },
                        colors = CheckboxDefaults.colors(checkedColor = PillGreen)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // [다음 버튼]
            Button(
                onClick = onNextClick,
                enabled = isNextEnabled, // 필수 항목이 체크되어야만 true
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    disabledContainerColor = Color.LightGray // 비활성화 시 회색
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "다음",
                    fontSize = 18.sp,
                    color = if (isNextEnabled) Color.White else Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
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

    // 정규식: 010-숫자4개-숫자4개 형태인지 검사
    val regex = Regex("^010-\\d{4}-\\d{4}$")
    val isPhoneValid = regex.matches(phoneNumber)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(), // 키보드 대응
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            JoinHeader(title = "", onBackClick = onBackClick)
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

            // [입력 필드]
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    // 13자리(010-0000-0000)까지만 입력 가능하도록 제한
                    if (it.length <= 13) {
                        phoneNumber = it
                    }
                },
                label = { Text("휴대폰 번호 입력") },
                placeholder = { Text("010-0000-0000") }, // 힌트 텍스트 추가
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // 숫자 키패드
                singleLine = true,
                isError = phoneNumber.isNotEmpty() && !isPhoneValid, // 형식이 틀리면 빨간 테두리
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = PillGreen,
                    unfocusedBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red
                )
            )

            // [안내 문구]
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "번호는 010-0000-0000형태로 작성해주세요.",
                color = if (phoneNumber.isEmpty() || isPhoneValid) Color.Gray else Color.Red,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // [다음 버튼]
            Button(
                onClick = onNextClick,
                enabled = isPhoneValid, // 형식이 맞을 때만 활성화
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    disabledContainerColor = Color.LightGray // 비활성화일 때 회색
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "인증 문자 받기",
                    fontSize = 18.sp,
                    // 활성화 여부에 따라 글자색 변경
                    color = if (isPhoneValid) Color.White else Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
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
    val context = LocalContext.current // 토스트 메시지를 띄우기 위한 컨텍스트

    // --- 상태 변수들 ---
    var inputCode by remember { mutableStateOf("") }        // 사용자가 입력한 코드
    var generatedCode by remember { mutableStateOf("") }    // 랜덤 생성된 정답 코드
    var timeLeft by remember { mutableStateOf(180) }        // 남은 시간 (초 단위, 3분 = 180초)

    // 인증번호 발송 함수
    fun sendVerificationCode() {
        // 1. 6자리 랜덤 숫자 생성 (100000 ~ 999999)
        val newCode = (100000..999999).random().toString()
        generatedCode = newCode

        // 2. 타이머 리셋
        timeLeft = 180

        // 3. 메시지 발송 (테스트용 토스트 메시지 출력)
        // 실제 앱에서는 여기서 SMS API를 호출하면 됩니다.
        Toast.makeText(context, "pill 회원가입 인증번호는 ${newCode} 입니다.", Toast.LENGTH_LONG).show()
    }

    // 화면이 처음 켜질 때 인증번호 1회 발송
    LaunchedEffect(Unit) {
        sendVerificationCode()
    }

    // 타이머 로직 (1초마다 감소)
    LaunchedEffect(generatedCode) { // generatedCode가 갱신될 때(재전송 시) 타이머 로직 재시작
        while (timeLeft > 0) {
            delay(1000L) // 1초 대기
            timeLeft--
        }
    }

    // '다음' 버튼 활성화 조건: 입력값 == 정답 AND 시간 > 0
    val isNextEnabled = (inputCode == generatedCode && timeLeft > 0)

    // 시간 포맷팅 (03:00 형태)
    val timeText = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            JoinHeader(title = "", onBackClick = onBackClick)
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

            // [전화번호 표시] (읽기 전용)
            OutlinedTextField(
                value = "010-1234-5678",
                onValueChange = {},
                readOnly = true,
                label = { Text("입력한 휴대폰 번호") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = InputGray,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // [인증번호 입력창]
            OutlinedTextField(
                value = inputCode,
                onValueChange = {
                    // 6자리까지만 입력 가능
                    if (it.length <= 6) inputCode = it
                },
                label = { Text("인증번호 6자리") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                // 입력창 오른쪽에 타이머 표시
                trailingIcon = {
                    Text(
                        text = timeText,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = PillGreen,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            // [재전송 버튼]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // 오른쪽 정렬
            ) {
                TextButton(onClick = { sendVerificationCode() }) {
                    Text("인증번호 재전송", color = Color.Gray, fontSize = 13.sp, textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // [다음 버튼]
            Button(
                onClick = onNextClick,
                enabled = isNextEnabled, // 조건 불충족 시 비활성화
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "다음",
                    fontSize = 18.sp,
                    color = if (isNextEnabled) Color.White else Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding() // 시스템 바 패딩 추가
            .imePadding()        // 키보드 패딩 추가
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState) // 스크롤
    ) {
        JoinHeader(title = "", onBackClick = onBackClick)
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
// [회원가입 4] 닉네임 입력 및 저장 (수정됨)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinScreen4(
    // 이전 화면에서 넘겨받은 정보들
    email: String,
    password: String,
    name: String,
    phoneNumber: String,
    onFinishClick: () -> Unit
) {
    var nickname by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            JoinHeader(title = "", onBackClick = null)

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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = PillGreen
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // 1. 입력받은 모든 정보를 묶어서 저장 (회원가입 처리)
                    val newUser = UserAccount(
                        email = email,
                        password = password,
                        name = name,
                        phoneNumber = phoneNumber,
                        nickname = nickname
                    )
                    UserManager.register(newUser) // 저장소에 등록!

                    // 2. 완료 후 첫 화면(로그인)으로 이동
                    onFinishClick()
                },
                // 닉네임을 한 글자라도 써야 버튼 활성화
                enabled = nickname.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "회원가입 완료",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}