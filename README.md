# OOP (객체지향프로그래밍)
# Pill - 의약품 정보 관리 앱

의약품 정보를 검색하고 복용 기록을 관리할 수 있는 Android 애플리케이션입니다.

## 📱 프로젝트 개요

이 프로젝트는 공공데이터 API를 활용하여 의약품 정보를 조회하고, 사용자의 복용 기록을 캘린더로 관리할 수 있는 앱입니다. Firebase를 통한 사용자 인증 및 데이터 저장, Room Database를 통한 로컬 데이터 관리 기능을 제공합니다.

## ✨ 주요 기능

### 1. 사용자 인증
- Firebase Authentication을 통한 회원가입 및 로그인
- 회원가입 프로세스 (4단계)

### 2. 의약품 검색
- 공공데이터 API를 통한 의약품 정보 검색
- 키워드 기반 검색 기능
- 검색 결과 상세 정보 조회
- 의약품 이미지 표시 (Coil 라이브러리 사용)

### 3. 즐겨찾기
- 의약품 즐겨찾기 추가/제거
- Room Database를 통한 로컬 저장
- Firebase Firestore를 통한 클라우드 동기화

### 4. 복용 기록 관리
- 캘린더를 통한 복용 기록 시각화
- 일별 복용 여부 기록
- 캘린더 상세 화면에서 의약품별 복용 상태 확인

### 5. 홈 화면
- 주요 기능 접근
- 사용자 정보 표시

## 🛠 기술 스택

### 프레임워크 & 라이브러리
- **언어**: Kotlin
- **UI**: Jetpack Compose
- **아키텍처**: MVVM (Model-View-ViewModel)
- **비동기 처리**: Kotlin Coroutines
- **의존성 주입**: KSP (Kotlin Symbol Processing)

### 네트워크
- **Retrofit 2.9.0**: REST API 통신
- **Gson**: JSON 직렬화/역직렬화
- **OkHttp Logging Interceptor**: 네트워크 요청 로깅

### 데이터베이스
- **Room Database 2.6.1**: 로컬 데이터 저장 (즐겨찾기 등)
- **Firebase Firestore**: 클라우드 데이터 저장 (사용자 정보, 복용 기록 등)

### 인증
- **Firebase Authentication**: 사용자 인증

### 이미지 처리
- **Coil 2.7.0**: 이미지 로딩 및 캐싱

### UI 컴포넌트
- **Material 3**: Material Design 3 컴포넌트
- **Calendar View 2.7.0**: 캘린더 UI
- **Navigation Compose**: 화면 전환 관리

## 📁 프로젝트 구조

```
app/src/main/java/com/example/oop/
├── data/
│   ├── api/                    # API 관련
│   │   ├── MedicineApiClient.kt
│   │   ├── MedicineApiDataSource.kt
│   │   ├── MedicineApiResponse.kt
│   │   ├── MedicineApiService.kt
│   │   └── FirebaseDataSource.kt
│   ├── model/                  # 데이터 모델
│   │   ├── Medicine.kt
│   │   ├── User.kt
│   │   ├── Favorite.kt
│   │   └── DailyLog.kt
│   ├── repository/            # 데이터 저장소
│   │   ├── SearchRepository.kt
│   │   └── CalendarDetailRepository.kt
│   └── MedicineDatabase.kt    # Room Database
├── ui/
│   ├── onBoarding/            # 온보딩 화면
│   │   ├── LoginScreen.kt
│   │   ├── JoinScreen.kt
│   │   └── LoadingScreen.kt
│   ├── home/                  # 홈 화면
│   ├── Search/                # 검색 화면
│   ├── searchResult/          # 검색 결과 화면
│   ├── medicineDetail/        # 의약품 상세 화면
│   ├── calendar/              # 캘린더 화면
│   ├── calendarDetail/        # 캘린더 상세 화면
│   └── theme/                 # 테마 설정
└── MainActivity.kt            # 메인 액티비티
```

## 🚀 시작하기

### 사전 요구사항
- Android Studio Hedgehog 이상
- JDK 11 이상
- Android SDK 26 이상 (minSdk: 26, targetSdk: 36)
- Firebase 프로젝트 설정

### 설치 방법

1. **저장소 클론**
```bash
git clone [repository-url]
cd OOP
```

2. **Firebase 설정**
   - Firebase Console에서 프로젝트 생성
   - `google-services.json` 파일을 `app/` 디렉토리에 추가

3. **API 키 설정**
   - 프로젝트 루트에 `local.properties` 파일 생성
   - 공공데이터 API 키 추가:
   ```
   API_KEY=your_api_key_here
   ```

4. **프로젝트 빌드**
```bash
./gradlew build
```

5. **앱 실행**
   - Android Studio에서 프로젝트 열기
   - 에뮬레이터 또는 실제 기기에서 실행

## 📝 환경 설정

### local.properties
프로젝트 루트에 `local.properties` 파일을 생성하고 다음 내용을 추가하세요:

```properties
API_KEY=your_public_data_api_key
```

API 키는 `build.gradle.kts`에서 자동으로 읽어와 `BuildConfig.API_KEY`로 사용됩니다.

### Firebase 설정
1. Firebase Console에서 Android 앱 등록
2. `google-services.json` 파일 다운로드
3. `app/google-services.json`에 파일 복사

## 🏗 아키텍처

이 프로젝트는 MVVM (Model-View-ViewModel) 아키텍처 패턴을 따릅니다:

- **Model**: 데이터 모델 및 데이터 소스 (API, Database)
- **View**: Jetpack Compose UI 컴포넌트
- **ViewModel**: UI 상태 관리 및 비즈니스 로직

### 데이터 흐름
1. View에서 사용자 액션 발생
2. ViewModel이 Repository를 통해 데이터 요청
3. Repository가 API 또는 Database에서 데이터 조회
4. 데이터가 ViewModel의 State로 변환
5. View가 State를 관찰하여 UI 업데이트


## 📄 라이선스

이 프로젝트는 교육 목적으로 개발되었습니다.

## 👥 기여자

프로젝트에 기여해주신 모든 분들께 감사드립니다.
- taerimiiii
- ryudonghee
- flashbomb625
- leehy-1839
