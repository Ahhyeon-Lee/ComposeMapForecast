object Versions {
    const val ANDROID_GRADLE = "7.2.2"
    const val KOTLIN = "1.6.10"
    const val APP_COMPAT = "1.4.2"
    const val LIFECYCLE = "2.3.1"
    const val ACTIVITY = "1.3.1"
    const val CORE = "1.7.0"
    const val MATERIAL = "1.6.1"
    const val JUNIT = "1.1.3"
    const val ESPRESSO_CORE = "3.4.0"
    const val COMPOSE = "1.2.0-alpha01"
    const val HILT = "2.38.1"
    const val COROUTINE = "1.6.1"
    const val RETROFIT = "2.9.0"
    const val OKHTTP = "3.11.0"
    const val ROOM = "2.4.3"
    const val PERMISSION = "0.26.4-beta"
}

object Libraries {

    object AndroidX {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val CORE = "androidx.core:core-ktx:${Versions.CORE}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
        const val ACTIVITY = "androidx.activity:activity-compose:${Versions.ACTIVITY}"

        const val JUNIT = "androidx.test.ext:junit:${Versions.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object Compose {
        const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL_ICONS = "androidx.compose.material:material-icons-extended:${Versions.COMPOSE}"
        const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val COMPOSE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0-rc01"
        const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:2.5.1"
        const val COMPOSE_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"
        const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_MAPS = "com.google.maps.android:maps-compose:2.5.3"
    }

    object Google {
        const val MAP = "com.google.android.gms:play-services-maps:18.0.2"
        const val LOCATION = "com.google.android.gms:play-services-location:20.0.0"
        const val PERMISSION = "com.google.accompanist:accompanist-permissions:${Versions.PERMISSION}"
    }

    object Hilt {
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val HILT_VIEWMODEL = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val HILT_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    }

    object Coroutine {
        const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
        const val FLOW_ADAPTER = "tech.thdev:flow-call-adapter-factory:1.0.0"
    }

    object Retrofit {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
        const val CONVERTER_SCALARS = "com.squareup.retrofit2:co}nverter-scalars:${Versions.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object Room {
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
    }

    object ExcelReader {
        const val FASTEXCEL = "org.dhatim:fastexcel:0.12.3"
        const val FASTEXCEL_READER = "org.dhatim:fastexcel-reader:0.12.3"
        const val JEXCEL = "net.sourceforge.jexcelapi:jxl:2.6.12"
    }

}