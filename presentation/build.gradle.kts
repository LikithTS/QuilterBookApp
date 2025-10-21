plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.likith.presentation"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures { compose = true }
}

kotlin {
    jvmToolchain(17)

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-sensitive-resolution")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3)

    //Compose
    implementation(platform(libs.androidx.compose.bom))

    //Viewmodel
    implementation(libs.viewmodel.compose)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.network.ktor2)
    implementation(libs.coil.network.ktor3)

    //Card
    implementation(libs.cardview)

    //adaptive screen
    implementation(libs.material3.adaptive)

    //Preview
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)


    //Dependency Injection
    implementation(libs.koin.core)

    implementation(project(":domain"))
}