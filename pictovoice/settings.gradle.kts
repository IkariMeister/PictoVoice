@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PictoVoice"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":shared:core-model")
include(":shared:core-ui")
include(":shared:core-telemetry")
include(":shared:feature-vocabulary")
include(":shared:feature-communication")
include(":composeApp")
