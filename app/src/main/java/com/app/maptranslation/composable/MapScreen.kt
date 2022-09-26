package com.app.maptranslation.composable

import android.os.Bundle
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.emoji2.text.EmojiCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.maptranslation.R
import com.app.maptranslation.viewmodel.MapScreenViewModel
import com.app.maptranslation.viewmodel.TranslateViewModel
import com.example.domain.model.Regions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.*

const val HOME_SCREEN = "HomeScreen"
const val MAP_SCREEN = "MapScreen"
const val MAP_HISTORY_SCREEN = "MapHistoryScreen"
const val TRANSLATE_SCREEN = "TranslateScreen"
const val TRANSLATE_HISTORY_SCREEN = "TranslateHistoryScreen"

@Composable
fun MyApp(
    mapViewModel: MapScreenViewModel = viewModel(),
    translateViewModel: TranslateViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        composable(HOME_SCREEN) {
            HomeScreen(navController, mapViewModel)
        }
        composable(MAP_SCREEN) {
            MapScreen(navController, mapViewModel)
        }
        composable(MAP_HISTORY_SCREEN) {

        }
        composable(TRANSLATE_SCREEN) {
            TranslateScreen(translateViewModel)
        }
        composable(TRANSLATE_HISTORY_SCREEN) {

        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MapScreenViewModel) {
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
                        getLocation(context)
                        viewModel.checkRegionsData(context.applicationContext)
                        navController.navigate(MAP_SCREEN)
                    }
                }) {
                    Text(text = "지도")
                }
                Button(onClick = {
                    navController.navigate(MAP_HISTORY_SCREEN)
                }) {
                    Text(text = "지도 히스토리")
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    navController.navigate(TRANSLATE_SCREEN)
                }) {
                    Text(text = "번역")
                }
                Button(onClick = {
                    navController.navigate(TRANSLATE_HISTORY_SCREEN)
                }) {
                    Text(text = "번역 히스토리")
                }
            }
        }
    }
}

fun getLocation(context: Context) {
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
                Log.i("아현", "lat : ${location.latitude} / long : ${location.longitude}")
            }
    }

}

@Composable
fun MapScreen(navController: NavController, viewModel: MapScreenViewModel) {
//    getLocation()
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapBox(viewModel)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        TextFieldBox(navController, viewModel)
    }
}

@Composable
fun GoogleMapBox(viewModel: MapScreenViewModel) {
    val weatherData = viewModel.weatherState
    val location = LatLng(
        weatherData.latitude.toDouble(),
        weatherData.longtitude.toDouble()
    )
    val cameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(location, 10f)
    )
    var uiSettings by remember { mutableStateOf(
        MapUiSettings(
            myLocationButtonEnabled = true
        ))
    }
    var properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = location),
                title = weatherData.getMarkTitle(),
                icon = viewModel.drawBitmap(weatherData.icon),
                snippet = ""
            )
        }

        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            }
        )
    }
}

@Composable
fun TextFieldBox(navController: NavController, viewModel: MapScreenViewModel) {
    val (text, setText) = remember { mutableStateOf("") }

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
                    value = text,
                    onValueChange = setText,
                    placeholder = { Text(text = "시, 구, 동 검색") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.DarkGray,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                IconButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                        tint = colorResource(id = R.color.sky),
                        contentDescription = "VoiceBtn"
                    )
                }
                IconButton(
                    modifier = Modifier.wrapContentSize(),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = colorResource(id = R.color.sky),
                        contentDescription = "SearchBtn"
                    )
                }
            }
        }
        if (text.isNotEmpty()) {
            DropDownLazyColumn(
                viewModel.getRegionsList(text).takeIf {
                    it.isNotEmpty()
                } ?: listOf(Regions("검색 결과가 없습니다.")),
                viewModel
            )
        }
    }
}

@Composable
fun DropDownLazyColumn(regionsList:List<Regions>, viewModel: MapScreenViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .requiredHeightIn(max = 150.dp)
            .padding(horizontal = 40.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.Start
    ) {
        items(regionsList) { region ->
            DropDownItem(region = region, regionsList = regionsList, viewModel)
        }
    }
}

@Composable
fun DropDownItem(region:Regions, regionsList: List<Regions>, viewModel: MapScreenViewModel) {
    Surface(
        modifier = Modifier.clickable {
            viewModel.getWeatherInfo(region)
        }
    ) {
        Text(
            text = "${region.city} ${region.gu} ${region.dong}",
            modifier = Modifier.padding(10.dp)
        )
        if (regionsList.indexOf(region) != regionsList.lastIndex) {
            Divider(color = colorResource(R.color.light_gray), thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 10.dp))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
//    HomeScreen(navController)
}

@Preview
@Composable
fun MapScreenPreview() {
    val navController = rememberNavController()
    val mapViewModel:MapScreenViewModel = viewModel()
    MapScreen(navController, mapViewModel)
}

@Preview
@Composable
fun TextFieldAreaPreview() {
    val navController = rememberNavController()
    val mapViewModel:MapScreenViewModel = viewModel()
    TextFieldBox(navController, mapViewModel)
}