package com.example.foodcoma

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodcoma.ui.screens.CategoryDetailScreen
import com.example.foodcoma.ui.screens.CategoryListScreen
import com.example.foodcoma.viewmodel.FoodComaViewModel


enum class MovieDBScreen(@StringRes val title: Int){
    Categories(title = R.string.categories),
    CategoryDetail(title = R.string.category_detail),
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
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
){

    val foodComaViewModel: FoodComaViewModel = viewModel(factory = FoodComaViewModel.Factory)
    Log.d("FoodComaApp", "got past viewmodel factory")
    Scaffold(
        bottomBar = { FoodComaBottomBar() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.Categories.name,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MovieDBScreen.Categories.name) {
                CategoryListScreen(
                    categoryUiState = foodComaViewModel.categoryListUiState,
                    onCategoryClick = { category ->
                        foodComaViewModel.setSelectedCategory(category)
                        navController.navigate(MovieDBScreen.CategoryDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.CategoryDetail.name) {
                CategoryDetailScreen(
                    viewModel = foodComaViewModel,
                    onRecipeClick = { recipeID ->
                        foodComaViewModel.setSelectedRecipe(recipeID)
                    }
                )
            }

        }
    }
}

