plugins {
    kotlin("jvm") version "1.9.10" apply false
    kotlin("android") version "1.9.10" apply false
    id("com.android.application") version "8.1.0" apply false
    // НЕ указывай kotlin("plugin.compose") здесь
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}


