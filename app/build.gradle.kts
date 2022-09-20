plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.JETBRAINS_ANDROID)
    id(Plugins.MAPS_GRADLE)
    kotlin(Plugins.KAPT)
    id(Plugins.HILT_GRADLE)
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Libraries.AndroidX.APP_COMPAT)
    implementation(Libraries.AndroidX.LIFECYCLE)
    implementation(Libraries.AndroidX.ACTIVITY)
    implementation(Libraries.AndroidX.CORE)
    testImplementation(Libraries.Test.JUNIT)
    androidTestImplementation(Libraries.AndroidX.JUNIT)
    androidTestImplementation(Libraries.AndroidX.ESPRESSO_CORE)

    // Compose
    implementation(Libraries.Compose.COMPOSE_UI)
    implementation(Libraries.Compose.COMPOSE_MATERIAL)
    implementation(Libraries.Compose.COMPOSE_PREVIEW)
    implementation(Libraries.Compose.COMPOSE_VIEW_MODEL)
    implementation(Libraries.Compose.COMPOSE_NAVIGATION)
    androidTestImplementation(Libraries.Compose.COMPOSE_JUNIT)
    debugImplementation(Libraries.Compose.COMPOSE_UI_TOOLING)
    debugImplementation(Libraries.Compose.COMPOSE_MANIFEST)

    // Coroutine
    implementation(Libraries.Coroutine.COROUTINE_ANDROID)

    // Room
    implementation(Libraries.Room.ROOM_RUNTIME)
    kapt(Libraries.Room.ROOM_COMPILER)
    implementation(Libraries.Room.ROOM_KTX)

    // MapView
    implementation(Libraries.Google.MAP)
    implementation(Libraries.Google.LOCATION)

    // Hilt
    implementation(Libraries.Hilt.HILT_ANDROID)
    implementation(Libraries.Hilt.HILT_VIEWMODEL)
    kapt(Libraries.Hilt.HILT_COMPILER)

    implementation(project(":domainLayer"))
    implementation(project(":dataLayer"))
}