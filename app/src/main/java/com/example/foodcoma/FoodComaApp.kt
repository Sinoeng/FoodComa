package com.example.foodcoma

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


enum class MovieDBScreen(@StringRes val title: Int){
    Browse(title = R.string.browse),
    Favorites(title = R.string.favorites)
}

@Composable
fun FoodComaBottomBar(){
    BottomAppBar {

    }
}

@Composable
fun FoodComaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    Scaffold(
        bottomBar = { FoodComaBottomBar() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.Browse.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MovieDBScreen.Browse.name) {

            }

        }
    }
}

