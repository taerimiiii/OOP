package com.example.oop.ui // 1. [중요] 패키지 주소 수정

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.* // 여기서 OutlinedTextFieldDefaults를 가져옵니다
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oop.R // 로고 이미지를 위해 필요 (R.drawable.logo)

// 2. [중요] 색상 코드가 없어서 에러 날까 봐 여기에도 추가해둠
val PillGreen_Login = Color(0xFF8BC34A)
val ButtonGreen_Login = Color(0xFFC0F56F)

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onJoinClick: () -> Unit,
    onFindPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 로고 이미지 (없으면 텍스트로 대체됨)
        // Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo")
        Text(
            text = "pill",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = PillGreen_Login,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // 이메일 입력
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            // 3. [핵심 수정] TextFieldDefaults -> OutlinedTextFieldDefaults.colors 로 변경!
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = PillGreen_Login,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 비밀번호 입력
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            // 3. [핵심 수정] 여기도 똑같이 변경!
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = PillGreen_Login,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 로그인 버튼
        Button(
            onClick = {
                // UserManager 로그인 체크
                if (UserManager.login(email, password)) {
                    Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    onLoginClick()
                } else {
                    Toast.makeText(context, "이메일이나 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonGreen_Login),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "로그인",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 회원가입 & 비밀번호 찾기 링크
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "회원가입",
                color = Color.Gray,
                modifier = Modifier.clickable { onJoinClick() }
            )
            Text(text = "|", color = Color.LightGray)
            Text(
                text = "비밀번호 찾기",
                color = Color.Gray,
                modifier = Modifier.clickable { onFindPasswordClick() }
            )
        }
    }
}