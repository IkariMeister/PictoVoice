plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":shared:core-model"))
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
