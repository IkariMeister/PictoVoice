@file:OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.androidApplication)
}

kotlin {
    androidTarget()
    iosArm64().binaries.framework {
        baseName = "ComposeApp"
    }
    iosSimulatorArm64().binaries.framework {
        baseName = "ComposeApp"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:core-model"))
            implementation(project(":shared:core-telemetry"))
            implementation(project(":shared:core-ui"))
            implementation(project(":shared:feature-communication"))
            implementation(project(":shared:feature-vocabulary"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.uiToolingPreview)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

    }
}

android {
    namespace = "com.pictovoice"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.pictovoice"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    add("androidInstrumentedTestImplementation", compose.uiTestJUnit4)
    add("androidInstrumentedTestImplementation", "androidx.compose.ui:ui-test:1.7.5")
    add("androidInstrumentedTestImplementation", "androidx.compose.ui:ui-test-manifest:1.7.5")
    add("androidInstrumentedTestImplementation", libs.kotlin.test)
    add("androidInstrumentedTestImplementation", "androidx.test.ext:junit:1.2.1")
    add("androidInstrumentedTestImplementation", "androidx.test:runner:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.5")
}
