package com.app.maptranslation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.maptranslation.composables.MyApp
import com.app.maptranslation.viewmodels.MapScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mapViewModel by viewModels<MapScreenViewModel>()
            MyApp(mapViewModel)
        }
    }
}
