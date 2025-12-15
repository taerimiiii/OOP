// [사용자 정보 데이터 모델]
data class UserAccount(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val nickname: String
)

// [간이 데이터베이스 & 로그인 관리자]
object UserManager {
    // 가입한 유저들을 저장하는 리스트 (앱 끄면 사라짐, 실제 앱에선 Room/API 사용)
    private val userList = mutableListOf<UserAccount>()

    // 회원가입: 정보 저장
    fun register(user: UserAccount) {
        userList.add(user)
    }

    // 로그인: 이메일 비번이 일치하는지 확인
    fun login(email: String, pass: String): Boolean {
        return userList.any { it.email == email && it.password == pass }
    }
}