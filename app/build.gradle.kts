import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.productivity.wind"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.productivity.wind"
        minSdk = 28
        targetSdk = 35
        versionCode = 16
        versionName = "2.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val props = Properties()
            val secretPropsFile = rootProject.file("secret.properties")
            // The template file is used for CI/CD with debug keystore signing
            val templatePropsFile = rootProject.file("secret.template.properties")
            when {
                secretPropsFile.exists() -> {
                    println("🔑 Using secret.properties for signing config ✅ ")
                    props.load(secretPropsFile.inputStream())
                }
                templatePropsFile.exists() -> {
                    println("⚠️ Using secret.template.properties for signing config ⚠️ ")
                    props.load(templatePropsFile.inputStream())
                }
                else -> {
                    println("❌ No signing properties file found")
                }
            }
            val keystoreFile = props["KEYSTORE_FILE"] as String?
            if (!keystoreFile.isNullOrBlank()) {
                val ciKeystore =
                    System.getenv("CI")?.let {
                        val ciKeystorePath = rootProject.file("keystore/keep-alive.keystore")
                        if (ciKeystorePath.exists()) ciKeystorePath else null
                    }
                storeFile = ciKeystore ?: file(keystoreFile)
            }
            val resolvedStorePassword = System.getenv("KEYSTORE_PASSWORD") ?: props["KEYSTORE_PASSWORD"] as String?
            val resolvedKeyAlias = System.getenv("KEY_ALIAS") ?: props["KEY_ALIAS"] as String?
            val resolvedKeyPassword = System.getenv("KEY_PASSWORD") ?: props["KEY_PASSWORD"] as String?
            storePassword = resolvedStorePassword
            keyAlias = resolvedKeyAlias
            keyPassword = resolvedKeyPassword
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
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

ktlint {
    android.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
}



dependencies {

/*
[versions]
agp = "8.6.0"
composeBom = "2024.09.00"
composePlugin = "1.7.0-beta01"
coreKtx = "1.13.1"
espressoCore = "3.6.1"
junit = "4.13.2"
junitVersion = "1.2.1"
kotlin = "2.0.10"
ktlint = "12.1.1"
lifecycleRuntimeKtx = "2.8.5"
runtimeLivedata = "1.7.0"
appcompat = "1.7.1"
viewpager2 = "1.1.0"
material = "1.13.0"

*/
    implementation("androidx.compose.foundation:foundation:1.9.2")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.github.skydoves:balloon:1.6.13")
    implementation("androidx.compose.ui:ui")
    implementation("com.google.accompanist:accompanist-drawablepainter:0.37.3")
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.foundation:foundation")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")
    implementation("androidx.datastore:datastore-preferences:1.1.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.10")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.2")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    //implementation(libs.androidx.activity.compose)
implementation("androidx.activity:activity-compose:1.9.2")

    //implementation(libs.androidx.compose.runtime.livedata)
implementation("androidx.compose.runtime:runtime-livedata:1.7.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp3)
    implementation(libs.timber)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.viewpager2)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        suppressWarnings = true
    }
}
