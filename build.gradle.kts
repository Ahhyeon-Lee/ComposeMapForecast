buildscript {
    dependencies {
        classpath(Libraries.Hilt.HILT_GRADLE_PLUGIN)
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version(Versions.ANDROID_GRADLE) apply(false)
    id(Plugins.ANDROID_LIBRARY) version(Versions.ANDROID_GRADLE) apply(false)
    id(Plugins.JETBRAINS_ANDROID) version(Versions.KOTLIN) apply(false)
    id(Plugins.MAPS_GRADLE) version("2.0.1") apply(false)
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}