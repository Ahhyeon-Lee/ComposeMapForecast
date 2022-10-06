package com.app.maptranslation.composable

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.MapHistoryViewModel
import com.app.maptranslation.viewmodel.MapScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

const val HOME_SCREEN = "HomeScreen"
const val WEATHER_SEARCH_SCREEN = "WeatherSearchScreen"
const val WEATHER_MAP_SCREEN = "WeatherMapScreen/{date}"
const val MAP_HISTORY_SCREEN = "MapHistoryScreen"
const val TRANSLATE_SCREEN = "TranslateScreen"
const val TRANSLATE_HISTORY_SCREEN = "TranslateHistoryScreen"
const val CLOVA_TEST = "ClovaTest"

@Composable
fun MyApp(
    mapViewModel: MapScreenViewModel = hiltViewModel(),
    mapHistoryViewModel: MapHistoryViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        composable(HOME_SCREEN) {
            HomeScreen(navController, mapViewModel, mapHistoryViewModel)
        }
        composable(WEATHER_SEARCH_SCREEN) {
            MapScreen(navController, mapViewModel)
        }
        composable(
            WEATHER_MAP_SCREEN,
            arguments = listOf(navArgument("date"){ type = NavType.StringType })
        ) { backStackEntry ->
            val historyDate = backStackEntry.arguments?.getString("date")
            historyDate?.let { mapViewModel.getADayWeatherHistoryList(it) }
            GoogleMapBox(navController, mapHistoryViewModel, mapViewModel.weatherHistroyList)
        }
        composable(MAP_HISTORY_SCREEN) {
            MapHistoryScreen(navController, mapHistoryViewModel)
        }
        composable(TRANSLATE_SCREEN) {
            TranslateScreen()
        }
        composable(TRANSLATE_HISTORY_SCREEN) {
            HistoryScreen()
        }
        composable(CLOVA_TEST) {
            ClovaTest()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mapViewModel: MapScreenViewModel,
    mapHistoryViewModel: MapHistoryViewModel
) {
    val context = LocalContext.current

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    checkPermissions(locationPermissionState) {
                        mapViewModel.checkDbAndInsertData(context.applicationContext)
                        markCurrentLocWeatherInfo(context, mapViewModel)
                        navController.navigate(WEATHER_SEARCH_SCREEN)
                    }
                }) {
                    Text(text = stringResource(id = R.string.map))
                }
                Button(onClick = {
                    mapHistoryViewModel.getAllWeatherHistoryList()
                    navController.navigate(MAP_HISTORY_SCREEN)
                }) {
                    Text(text = stringResource(id = R.string.map_history))
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    navController.navigate(TRANSLATE_SCREEN)
                }) {
                    Text(text = stringResource(id = R.string.translate))
                }
                Button(onClick = {
                    navController.navigate(TRANSLATE_HISTORY_SCREEN)
                }) {
                    Text(text = stringResource(id = R.string.translate_history))
                }
            }
            Button(onClick = {
                navController.navigate(CLOVA_TEST)
            }) {
                Text(text = stringResource(id = R.string.clova_stt))
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun checkPermissions(
    permissionsState: MultiplePermissionsState,
    afterPermissionGranted: () -> Unit
) {
    if (permissionsState.allPermissionsGranted) { // 퍼미션 허용
        afterPermissionGranted.invoke()
    } else {
        val allPermissionsRevoked =
            permissionsState.permissions.size ==
                    permissionsState.revokedPermissions.size

        if (!allPermissionsRevoked) {
            // If not all the permissions are revoked, it's because the user accepted the COARSE
            // location permission, but not the FINE one.
        } else if (permissionsState.shouldShowRationale) {
            // Both location permissions have been denied
        } else {
            // First time the user sees this feature or the user doesn't want to be asked again
        }

        permissionsState.launchMultiplePermissionRequest()
    }
}

fun setTextFieldValueCursor(text: String) : TextFieldValue {
    return TextFieldValue(
        text = text,
        selection = TextRange(text.length)
    )
}