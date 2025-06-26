plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Room database configs.
    id("kotlin-kapt")
}

android {
    namespace = "com.example.data_in_android_practice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.data_in_android_practice"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room database dependencies.
    val room_version = "2.6.1"
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)
    // optional - RxJava2 support for Room
    implementation(libs.room.rxjava2)
    // optional - RxJava3 support for Room
    implementation(libs.room.rxjava3)
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.room.guava)
    // optional - Test helpers
    testImplementation(libs.room.testing)
    // optional - Paging 3 Integration
    implementation(libs.room.paging)

    // Datastore dependencies.
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)


}