package com.app.maptranslation.composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.maptranslation.R
import com.app.maptranslation.util.BitmapDrawHelper
import com.app.maptranslation.viewmodel.MapScreenViewModel
import com.app.maptranslation.viewmodel.SttRecognizerViewModel
import com.example.domain.model.Regions
import com.example.domain.model.WeatherForecast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(navController: NavController, viewModel: MapScreenViewModel) {
    val weatherData = viewModel.weatherState
    val loading = viewModel.dbLoading
    GoogleMapBox(navController, viewModel, listOf(weatherData))
    if (loading) {
        LoadingScreen("지역 데이터를 로딩중입니다.\n잠시만 기다려주세요.")
    }
}

@Composable
fun LoadingScreen(text:String?=null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        text?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = it,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GoogleMapBox(
    navController : NavController,
    viewModel: ViewModel,
    markerList : List<WeatherForecast>
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val weatherData = markerList.firstOrNull()
    val location = LatLng(weatherData?.latitude ?: 0.0, weatherData?.longtitude ?: 0.0)
    val cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(location, if (markerList.size == 1) 10f else 6f))

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
                icon = BitmapDrawHelper.drawBitmapIcon(marker.icon),
                snippet = stringResource(marker.weatherMark)
            )
        }
    }

    if (viewModel is MapScreenViewModel) {
        TextFieldBox(navController, cameraPositionState, viewModel)
        CurrentLocButton(context, cameraPositionState, viewModel)
        if (viewModel.weatherApiLoading) {
            LoadingScreen("날씨를 검색중입니다.")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldBox(
    navController: NavController,
    cameraPositionState: CameraPositionState,
    viewModel: MapScreenViewModel,
    sttViewModel : SttRecognizerViewModel = viewModel(),
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var openSearchingList by remember { mutableStateOf(true) }
    val sttState by sttViewModel.recognizeState.collectAsStateWithLifecycleRemember(initial = RecognizeState.Ready)
    var textFieldValueState by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    sttState.takeIf { it is RecognizeState.Result }?.let {
        textFieldValueState = setTextFieldValueCursor((it as RecognizeState.Result).text)
        sttViewModel.setRecognizeState(RecognizeState.End)
    }

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
                        value = textFieldValueState,
                        onValueChange = {
                            openSearchingList = true
                            textFieldValueState = it
                        },
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
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = colorResource(id = R.color.sky)
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                openSearchingList = false
                                keyboardController?.hide()
                                viewModel.regionsList.getOrNull(0)?.let {
                                    textFieldValueState = setTextFieldValueCursor(it.address)
                                    searchWeatherInfo(viewModel, it, keyboardController, cameraPositionState)
                                }
                            }
                        )
                    )
                    SttRecognizer(
                        viewModel = sttViewModel,
                        languageCode = "ko"
                    )
                    IconButton(
                        modifier = Modifier.wrapContentSize(),
                        onClick = {
                            openSearchingList = false
                            keyboardController?.hide()
                            viewModel.regionsList.getOrNull(0)?.let {
                                textFieldValueState = setTextFieldValueCursor(it.address)
                                searchWeatherInfo(viewModel, it, keyboardController, cameraPositionState)
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

            if (textFieldValueState.text.isNotEmpty() && openSearchingList) {
                viewModel.getSearchingRegionsList(textFieldValueState.text)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .requiredHeightIn(max = 150.dp)
                        .padding(horizontal = 40.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start
                ) {
                    itemsIndexed(viewModel.regionsList) { index, region ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    textFieldValueState = setTextFieldValueCursor(region.address)
                                },
                            color = if (index == 0) colorResource(R.color.light_gray) else Color.White
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = getAnnotatedString(region.address, textFieldValueState.text, colorResource(R.color.sky)),
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_north_west_24),
                                    tint = colorResource(if (index == 0) R.color.darker_gray else R.color.light_gray),
                                    contentDescription = null)
                            }
                            if (index != viewModel.regionsList.lastIndex) {
                                Divider(color = colorResource(R.color.light_gray), thickness = 0.5.dp)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getAnnotatedString(text:String, boldText:String, color: Color) : AnnotatedString {
    return text.lastIndexOf(boldText).takeIf { it != -1 }?.let {
        AnnotatedString(text,listOf(AnnotatedString.Range(SpanStyle(color = color), it, it + boldText.length)))
    } ?: run {
        AnnotatedString(text)
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

@OptIn(ExperimentalComposeUiApi::class)
fun searchWeatherInfo(viewModel: MapScreenViewModel, regions: Regions, keyboardController: SoftwareKeyboardController?, cameraPositionState: CameraPositionState) {
    keyboardController?.hide()
    markWeatherInfo(viewModel, regions, cameraPositionState)
}

fun markWeatherInfo(viewModel: MapScreenViewModel, regions: Regions, cameraPositionState: CameraPositionState) {
    viewModel.getWeatherInfo(regions)
    cameraPositionState.move(CameraUpdateFactory.newLatLng(LatLng(regions.latitude, regions.longtitude)))
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
                if (!viewModel.dbLoading) {
                    viewModel.getCurrentLocWeatherInfo(location.longitude, location.latitude)
                }
            }
    }
}