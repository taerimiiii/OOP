package com.example.myapplication.ui.theme

// [사용자 정보 데이터 모델]
// 수정 가능한(var) 객체로 관리하거나, copy()를 사용하기 위해 data class 유지
data class UserAccount(
    val email: String,
    var password: String, // 비밀번호 변경을 위해 var로 선언
    val name: String,
    val phoneNumber: String,
    val nickname: String
)

// [간이 데이터베이스 & 로그인 관리자]
object UserManager {
    // 가입한 유저들을 저장하는 리스트
    private val userList = mutableListOf<UserAccount>()

    // 회원가입: 정보 저장
    fun register(user: UserAccount) {
        userList.add(user)
    }

    // 로그인: 아이디(이메일)와 비번이 일치하는지 확인
    fun login(email: String, pass: String): Boolean {
        return userList.any { it.email == email && it.password == pass }
    }

    // 사용자 확인: 이메일과 전화번호가 일치하는 회원이 있는지 확인
    fun verifyUser(email: String, phone: String): Boolean {
        return userList.any { it.email == email && it.phoneNumber == phone }
    }

    // 비밀번호 변경: 해당 이메일의 유저 비밀번호 업데이트
    fun updatePassword(email: String, newPass: String) {
        val user = userList.find { it.email == email }
        user?.password = newPass
    }
}