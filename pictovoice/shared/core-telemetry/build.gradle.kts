plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {}
    }
}

android {
    namespace = "com.pictovoice.core.telemetry"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
}
