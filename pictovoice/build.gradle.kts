plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    val detektBaselineFile = file("$rootDir/config/detekt/${project.path.replace(':', '_')}-baseline.xml")

    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        allRules = false
        parallel = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline = detektBaselineFile
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        setSource(
            files(
                fileTree(projectDir) {
                    include("src/**/*.kt", "src/**/*.kts")
                }
            )
        )
    }

    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
        setSource(
            files(
                fileTree(projectDir) {
                    include("src/**/*.kt", "src/**/*.kts")
                }
            )
        )
    }
}
