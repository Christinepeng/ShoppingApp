plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.example.shoppingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoppingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        // 如果需要 Java 的警告設定，這裡可以改用 lintOptions
    }
    kotlinOptions {
        jvmTarget = "1.8"
        // 啟用所有警告作為錯誤
        allWarningsAsErrors = false // 如果需要更嚴格，可以改為 true
//        freeCompilerArgs += arrayOf("-Xlint:deprecation")

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // navigation & Hilt
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.navigation.fragment)
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // 或者使用 Moshi

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    kapt ("com.google.dagger:hilt-compiler:2.44")

    implementation(libs.androidx.material.icons.extended)
    implementation ("com.google.mlkit:barcode-scanning:17.0.2")
    implementation ("io.coil-kt:coil-compose:2.3.0")




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}