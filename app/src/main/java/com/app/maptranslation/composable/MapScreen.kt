package com.app.maptranslation.composable

import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.maptranslation.R
import com.app.maptranslation.ui.theme.Map_translationTheme
import com.app.maptranslation.viewmodel.MapScreenViewModel
import com.example.domain.model.Regions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

const val HOME_SCREEN = "HomeScreen"
const val MAP_SCREEN = "MapScreen"
const val MAP_HISTORY_SCREEN = "MapHistoryScreen"
const val TRANSLATE_SCREEN = "TranslateScreen"
const val TRANSLATE_HISTORY_SCREEN = "TranslateHistoryScreen"

@Composable
fun MyApp(
    mapViewModel: MapScreenViewModel = viewModel()
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

        }
        composable(TRANSLATE_HISTORY_SCREEN) {

        }
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: MapScreenViewModel) {
    val context = LocalContext.current
    Map_translationTheme {
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
                        viewModel.checkRegionsData(context.applicationContext)
                        navController.navigate(MAP_SCREEN)
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
                Button(onClick = {
//                    viewModel.getWeatherInfo("57", "78","","")
                }) {
                    Text(text = "ApiTest")
                }
                Button(onClick = {
                    viewModel.checkRegionsData(context.applicationContext)
                }) {
                    Text(text = "ReadExcelTest")
                }
            }
        }
    }
}

@Composable
fun getLocation() {
    val context = LocalContext.current
    val locationManager = LocationServices.getFusedLocationProviderClient(context)

}

@Composable
fun MapScreen(navController: NavController, viewModel: MapScreenViewModel) {
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
    val map = rememberMapView()

    AndroidView(
        factory = { map },
        update = { mapView ->
            val weatherData = viewModel.weatherState
            mapView.getMapAsync { googleMap ->
                val location = LatLng(
                    weatherData.latitude.toDouble(),
                    weatherData.longtitude.toDouble()
                )
                val dragListener = object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDrag(p0: Marker) { }

                    override fun onMarkerDragEnd(p0: Marker) {
                        val position = p0.position
                        Log.i("아현", "$position")
                    }

                    override fun onMarkerDragStart(p0: Marker) { }

                }
                googleMap.addMarker(
                    MarkerOptions()
                        .draggable(true)
                        .position(location)
                        .icon(viewModel.drawBitmap(weatherData.icon))
                        .title(weatherData.getMarkTitle())
                )
                googleMap.setOnMarkerDragListener(dragListener)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
            }
        }
    )
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

@Composable
fun rememberMapView():MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> { }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return mapView
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