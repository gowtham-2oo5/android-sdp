plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.gows.sdp.client"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gows.sdp.client"
        minSdk = 35
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    implementation("androidx.credentials:credentials:1.2.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.0.1")

    // Firebase Authentication (if you need it to manage users in Firebase)
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.firebase:firebase-firestore-ktx") // For Kotlin



// Google Sign-In (for Google authentication)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // https://mvnrepository.com/artifact/com.google.android.material/material
    runtimeOnly("com.google.android.material:material:1.12.0")

    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-android-extensions-runtime
    runtimeOnly("org.jetbrains.kotlin:kotlin-android-extensions-runtime:2.1.10")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // or a newer version
    implementation("androidx.fragment:fragment-ktx:1.5.4") // Or higher, if available
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2") // or a newer version if available


}