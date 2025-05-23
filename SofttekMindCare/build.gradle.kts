plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}