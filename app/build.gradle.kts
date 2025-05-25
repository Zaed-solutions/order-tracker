plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.realm)
    alias(libs.plugins.firebase)
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.zaed.ordertracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zaed.ordertracker"
        minSdk = 26
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
    packaging {
        resources {
            excludes.add("META-INF/DEPENDENCIES")
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
    implementation(libs.material)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.androidx.adaptive.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.kotlin.compose.compiler.plugin)

    // Kotlinx-Serialization
    implementation(libs.kotlinx.serialization.json)
    // Kotlinx-DateTime
    implementation(libs.kotlinx.datetime)
    // Compose ViewModel Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose Navigation
    implementation(libs.androidx.navigation.compose)
    // Material3 Extended Icons
    implementation(libs.androidx.material.icons.extended)
    // Kotlinx-Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Coil
    implementation(libs.coil.compose)
    // Koin
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.datastore.preferences)
    // Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    implementation("com.google.api-client:google-api-client-android:1.33.0")
    implementation("com.google.api-client:google-api-client:2.0.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-drive:v3-rev20220815-2.0.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("org.apache.poi:poi-ooxml:5.3.0")
}