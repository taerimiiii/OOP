plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("com.google.gms.google-services")
}

// local.properties 파일에서 API_KEY 읽기
val localPropertiesFile = rootProject.file("local.properties")
val apiKey = if (localPropertiesFile.exists()) {                      // 파일이 존재하면면
    val lines = localPropertiesFile.readLines()                       // 파일 내용 읽기
    val apiKeyLine = lines.find { it.startsWith("API_KEY=") } // API_KEY= 로 시작하는 줄 찾기
    if (apiKeyLine != null) {
        apiKeyLine.substringAfter("API_KEY=").trim()        // API_KEY= 뒤의 값 추출 후 앞뒤 공백 제거
    } else {
        ""                                                            // 찾지 못하면 빈 문자열 반환
    }
} else {
    ""                                                                // 파일이 존재하지 않으면 빈 문자열 반환
}

android {
    namespace = "com.example.oop"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.oop"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // local.properties에서 API_KEY 읽기
        val properties = org.jetbrains.kotlin.konan.properties.Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.compose.ui.text)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // View 기반 캘린더
    implementation("com.kizitonwose.calendar:view:2.7.0")

    // Compose 기반 캘린더
    implementation("com.kizitonwose.calendar:compose:2.7.0")

    // Retrofit + Gson (공공데이터 API 통신용)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp 로깅 인터셉터
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutine (비동기 통신)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // coil (의약품 이미지 로드용)
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.4")

    // ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))

    // 인증 (회원가입/로그인)
    implementation("com.google.firebase:firebase-auth")

    // Cloud Firestore (유저, 즐겨찾기 의약품, 캘린더 복용 여부 등 저장)
    implementation("com.google.firebase:firebase-firestore")
}