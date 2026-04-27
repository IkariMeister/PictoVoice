plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {}
    }
}
