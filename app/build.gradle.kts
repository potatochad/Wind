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

    compileSdk = 36

    defaultConfig {
        applicationId = "com.productivity.wind"
        minSdk = 28
        targetSdk = 36
        versionCode = 16
        versionName = "2.2"

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
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlin {
        compilerOptions {
           jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
           freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.11"
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
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.media:media:1.7.1")


    implementation("com.google.maps.android:android-maps-utils:3.19.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps.android:maps-compose:6.9.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0") 
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("org.mozilla:rhino:1.7.15")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.10")
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
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.datastore:datastore-preferences:1.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("androidx.compose.ui:ui-graphics:1.7.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
}
