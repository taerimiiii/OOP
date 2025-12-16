package com.example.oop.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import com.example.oop.ui.theme.OOPTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindPasswordScreen(onBackClick: () -> Unit, onFinishClick: () -> Unit) {
    // 단계 관리 (1: 정보입력, 2: 인증번호, 3: 비번변경)
    var step by remember { mutableStateOf(1) }
    val context = LocalContext.current

    // 입력 데이터 상태들
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    // 인증번호 관련
    var authCodeInput by remember { mutableStateOf("") }
    var generatedCode by remember { mutableStateOf("") }
    var timeLeft by remember { mutableStateOf(180) }

    // 새 비밀번호 관련
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // 타이머 로직 (step 2일 때만 작동)
    LaunchedEffect(step, generatedCode) {
        if (step == 2) {
            timeLeft = 180
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
        }
    }

    // 인증번호 발송 함수
    fun sendCode() {
        val code = (100000..999999).random().toString()
        generatedCode = code
        timeLeft = 180
        Toast.makeText(context, "인증번호는 $code 입니다.", Toast.LENGTH_LONG).show()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // 헤더 (뒤로가기)
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Text("비밀번호 찾기", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 단계별 UI 변경
            when (step) {
                1 -> { // [1단계] 사용자 정보 입력
                    Text(
                        text = buildAnnotatedString {
                            append("가입하신\n")
                            withStyle(style = SpanStyle(color = PillGreen)) { append("이메일과 전화번호") }
                            append("를\n입력해주세요.")
                        },
                        fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email, onValueChange = { email = it },
                        label = { Text("이메일") },
                        modifier = Modifier.fillMaxWidth(), singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = phoneNumber, onValueChange = { phoneNumber = it },
                        label = { Text("휴대폰 번호 (하이픈 포함)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            // 정보 일치 확인
                            if (UserManager.verifyUser(email, phoneNumber)) {
                                step = 2
                                sendCode() // 인증번호 발송
                            } else {
                                Toast.makeText(context, "일치하는 회원 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = email.isNotEmpty() && phoneNumber.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("인증번호 받기", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                2 -> { // [2단계] 인증번호 입력
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = PillGreen)) { append("인증번호") }
                            append("를\n입력해주세요.")
                        },
                        fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = authCodeInput, onValueChange = { if (it.length <= 6) authCodeInput = it },
                        label = { Text("인증번호 6자리") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        trailingIcon = {
                            val min = timeLeft / 60
                            val sec = timeLeft % 60
                            Text(String.format("%02d:%02d", min, sec), color = Color.Red, modifier = Modifier.padding(end = 12.dp))
                        }
                    )

                    // 재전송 버튼
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { sendCode() }) {
                            Text("인증번호 재전송", color = Color.Gray, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (authCodeInput == generatedCode && timeLeft > 0) {
                                step = 3 // 인증 성공 시 비밀번호 변경 화면으로
                            } else {
                                Toast.makeText(context, "인증번호가 틀리거나 시간이 만료되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = authCodeInput.length == 6,
                        modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("인증 확인", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                3 -> { // [3단계] 비밀번호 재설정
                    Text(
                        text = buildAnnotatedString {
                            append("새로운\n")
                            withStyle(style = SpanStyle(color = PillGreen)) { append("비밀번호") }
                            append("를\n설정해주세요.")
                        },
                        fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = newPassword, onValueChange = { newPassword = it },
                        label = { Text("새 비밀번호") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(), singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = confirmPassword, onValueChange = { confirmPassword = it },
                        label = { Text("새 비밀번호 확인") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(), singleLine = true
                    )

                    // 불일치 안내
                    if (confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
                        Text("비밀번호가 일치하지 않습니다.", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // 완료 버튼
                    val isCompleteEnabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && (newPassword == confirmPassword)

                    Button(
                        onClick = {
                            // 비밀번호 업데이트 로직
                            UserManager.updatePassword(email, newPassword)
                            Toast.makeText(context, "비밀번호가 변경되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show()
                            onFinishClick() // 로그인 화면으로 복귀
                        },
                        enabled = isCompleteEnabled,
                        modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonGreen,
                            disabledContainerColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("비밀번호 변경 완료", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}