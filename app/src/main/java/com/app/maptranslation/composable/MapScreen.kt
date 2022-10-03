package com.app.maptranslation.composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.MapScreenViewModel
import com.app.maptranslation.viewmodel.SttRecognizerViewModel
import com.example.domain.model.Regions
import com.example.domain.model.TranslateHistoryData
import com.example.domain.model.WeatherForecast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

const val HOME_SCREEN = "HomeScreen"
const val WEATHER_SEARCH_SCREEN = "WeatherSearchScreen"
const val WEATHER_MAP_SCREEN = "WeatherMapScreen"
const val MAP_HISTORY_SCREEN = "MapHistoryScreen"
const val TRANSLATE_SCREEN = "TranslateScreen"
const val TRANSLATE_HISTORY_SCREEN = "TranslateHistoryScreen"
const val CLOVA_TEST = "ClovaTest"

@Composable
fun MyApp(
    mapViewModel: MapScreenViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        composable(HOME_SCREEN) {
            HomeScreen(navController, mapViewModel)
        }
        composable(WEATHER_SEARCH_SCREEN) {
            MapScreen(navController, mapViewModel)
        }
        composable(WEATHER_MAP_SCREEN) {
            GoogleMapBox(navController, mapViewModel, listOf())
        }
        composable(MAP_HISTORY_SCREEN) {
            MapHistoryScreen(navController)
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
    mapViewModel: MapScreenViewModel
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

@Composable
fun MapScreen(navController: NavController, viewModel: MapScreenViewModel) {
    if (!viewModel.dbLoading.value) {
        val weatherData = viewModel.weatherState
        GoogleMapBox(navController, viewModel, listOf(weatherData))
    } else {
        LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "지역 데이터를 로딩중입니다.\n잠시만 기다려주세요.",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GoogleMapBox(
    navController : NavController,
    viewModel: MapScreenViewModel,
    markerList : List<WeatherForecast>
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val weatherData = markerList[0]
    val location = LatLng(weatherData.latitude, weatherData.longtitude)
    val cameraPositionState by remember {
        mutableStateOf(
            CameraPositionState(CameraPosition.fromLatLngZoom(location, if (markerList.size == 1) 10f else 5f))
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            focusManager.clearFocus()
        }
    ) {
        cameraPositionState.move(CameraUpdateFactory.newLatLng(location))
        for (marker in markerList) {
            Marker(
                state = MarkerState(position = LatLng(marker.latitude, marker.longtitude)),
                title = marker.dong.takeIf { it.isNotEmpty() } ?: marker.gu.takeIf { it.isNotEmpty() } ?: marker.city,
                icon = viewModel.drawBitmapIcon(marker.icon),
                snippet = stringResource(marker.weatherMark)
            )
        }
    }

    TextFieldBox(navController, cameraPositionState, viewModel)

    CurrentLocButton(context, cameraPositionState, viewModel)
}

@Composable
fun TextFieldBox(
    navController: NavController,
    cameraPositionState: CameraPositionState,
    viewModel: MapScreenViewModel,
    sttViewModel : SttRecognizerViewModel = viewModel(),
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(shape = CircleShape, elevation = 5.dp) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = Color.DarkGray,
                            contentDescription = "BackBtn")
                    }
                    TextField(
                        value = sttViewModel.sttText.value,
                        onValueChange = { sttViewModel.setSttText(it) },
                        placeholder = { Text(text = stringResource(id = R.string.address_input)) },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                            .padding(horizontal = 0.dp)
                            .focusRequester(focusRequester)
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.DarkGray,
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        keyboardActions = KeyboardActions(

                        )
                    )
                    SttRecognizer(
                        viewModel = sttViewModel,
                        languageCode = "ko"
                    )
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            viewModel.regionsList.takeIf { it.isNotEmpty() }?.let {
                                sttViewModel.setSttText(viewModel.regionsList[0].address)
                                markWeatherInfo(viewModel, viewModel.regionsList[0], cameraPositionState)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = colorResource(id = R.color.sky),
                            contentDescription = "SearchBtn"
                        )
                    }
                }
            }

            if (sttViewModel.sttText.value.isNotEmpty()) {
                viewModel.getSearchingRegionsList(sttViewModel.sttText.value)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .requiredHeightIn(max = 150.dp)
                        .padding(horizontal = 40.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(viewModel.regionsList) { region ->
                        Surface(
                            modifier = Modifier.clickable {
                                markWeatherInfo(viewModel, region, cameraPositionState)
                                sttViewModel.setSttText(region.address)
                            }
                        ) {
                            Text(
                                text = viewModel.regionsList.takeIf { it.isNotEmpty() }?.let { region.address } ?: "검색 결과가 없습니다.",
                                modifier = Modifier.padding(10.dp)
                            )
                            if (viewModel.regionsList.indexOf(region) != viewModel.regionsList.lastIndex) {
                                Divider(color = colorResource(R.color.light_gray), thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrentLocButton(
    context: Context,
    cameraPositionState: CameraPositionState,
    viewModel: MapScreenViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, bottom = 40.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(colorResource(id = R.color.sky)),
            onClick = {
                markCurrentLocWeatherInfo(context, viewModel, cameraPositionState)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_gps_fixed_24),
                tint = colorResource(id = R.color.white),
                contentDescription = "CurrentLocation"
            )
        }
    }
}

fun markCurrentLocWeatherInfo(context: Context, viewModel: MapScreenViewModel, cameraPositionState: CameraPositionState? = null) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                Log.i("아현", "long : ${location.longitude} / lat : ${location.latitude}")
                cameraPositionState?.move(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
                if (!viewModel.dbLoading.value) {
                    viewModel.getCurrentLocWeatherInfo(location.longitude, location.latitude)
                }
            }
    }
}

fun markWeatherInfo(viewModel: MapScreenViewModel, regions: Regions, cameraPositionState: CameraPositionState) {
    viewModel.getWeatherInfo(regions)
    cameraPositionState.move(CameraUpdateFactory.newLatLng(LatLng(regions.latitude, regions.longtitude)))
}