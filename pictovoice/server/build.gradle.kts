plugins {
    alias(libs.plugins.kotlinJvm)
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    testImplementation(libs.kotlin.test)
    testImplementation(libs.swagger.parser)
}

tasks.test {
    useJUnit()
}
