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

include(":shared:domain")
include(":shared:data")
include(":shared:presentation")
include(":composeApp")
include(":androidApp")
include(":iosApp")
include(":server")
