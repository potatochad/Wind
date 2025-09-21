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
        /*
         * DO NOT TARGET API 35 YET - IT WILL BREAK THE APP
         * Previously, if an app held the SYSTEM_ALERT_WINDOW permission, it could launch a foreground service
         * even if the app was currently in the background (as discussed in exemptions from background start restrictions).
         * If an app targets Android 15, this exemption is now narrower. The app now needs to have the SYSTEM_ALERT_WINDOW
         * permission and also have a visible overlay window. That is, the app needs to first launch a TYPE_APPLICATION_OVERLAY
         * window and the window needs to be visible before you start a foreground service.  If your app attempts to start
         * a foreground service from the background without meeting these new requirements (and it does not have some other exemption),
         * the system throws ForegroundServiceStartNotAllowedException.  If your app declares the SYSTEM_ALERT_WINDOW
         * permission and launches foreground services from the background, it may be affected by this change.
         * If your app gets a ForegroundServiceStartNotAllowedException, check your app's order of operations and make sure
         * your app already has an active overlay window before it attempts to start a foreground service from the background.
         * You can check if your overlay window is currently visible by calling View.getWindowVisibility(),
         * or you can override View.onWindowVisibilityChanged() to get notified whenever the visibility changes.
         */
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
    implementation 'com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.2'

    implementation("com.github.skydoves:balloon:1.6.13")
    implementation("org.mozilla.geckoview:geckoview:141.0.20250806102122")
    implementation("androidx.compose.ui:ui")
    // implementation("androidx.compose.ui:ui-text")
    // implementation("androidx.compose.ui:ui:1.8.2")
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



    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.runtime.livedata)
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
    implementation(platform(libs.androidx.compose.bom))
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
