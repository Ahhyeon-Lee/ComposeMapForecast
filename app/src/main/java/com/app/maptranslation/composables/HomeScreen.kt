package com.app.maptranslation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.maptranslation.ui.theme.Map_translationTheme

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "MapScreen"
    ) {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("MapScreen") {
            MapScreen()
        }
        composable("MapHistoryScreen") {

        }
        composable("TranslateScreen") {

        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
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
                        navController.navigate("MapScreen")
                    }) {
                        Text(text = "지도")
                    }
                    Button(onClick = {
                        navController.navigate("MapHistoryScreen")
                    }) {
                        Text(text = "지도 히스토리")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = {
                        navController.navigate("MapScreen")
                    }) {
                        Text(text = "번역")
                    }
                    Button(onClick = {
                        navController.navigate("MapHistoryScreen")
                    }) {
                        Text(text = "번역 히스토리")
                    }
                }
            }
        }
    }
}

@Composable
fun MapScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        TextFieldArea()
    }
}

@Composable
fun TextFieldArea() {
    val (text, setValue) = remember { mutableStateOf("") }
    Surface(shape = RoundedCornerShape(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackBtn")
            TextField(
                value = text,
                onValueChange = setValue,
                modifier = Modifier.fillMaxWidth()
            )
            Icon(imageVector = Icons.Default.Phone, contentDescription = "BackBtn")
            Icon(imageVector = Icons.Default.Search, contentDescription = "SearchBtn")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

@Preview
@Composable
fun MapScreenPreview() {
    val navController = rememberNavController()
    MapScreen()
}