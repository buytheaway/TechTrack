plugins {
    kotlin("jvm") version "1.9.10" apply false
    kotlin("android") version "1.9.10" apply false
    id("com.android.application") version "8.1.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "TechTrack"
include(":app")
