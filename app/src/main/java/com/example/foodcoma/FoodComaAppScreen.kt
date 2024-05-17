package com.example.foodcoma

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodcoma.ui.screens.AreaDetailScreen
import com.example.foodcoma.ui.screens.AreaListScreen
import com.example.foodcoma.ui.screens.CategoryDetailScreen
import com.example.foodcoma.ui.screens.CategoryListScreen
import com.example.foodcoma.ui.screens.IngredientDetailScreen
import com.example.foodcoma.ui.screens.IngredientListScreen
import com.example.foodcoma.ui.screens.RecipeDetailScreen
import com.example.foodcoma.ui.theme.BottomBarOrange
import com.example.foodcoma.viewmodel.FoodComaViewModel


enum class MovieDBScreen(@StringRes val title: Int){
    Categories(title = R.string.categories),
    Areas(title = R.string.areas),
    Ingredients(title = R.string.ingredients),
    CategoryDetail(title = R.string.category_detail),
    AreaDetail(title = R.string.area_detail),
    IngredientDetail(title = R.string.ingredient_detail),
    RecipeDetail(title = R.string.recipe_detail),
    Browse(title = R.string.browse),
    Favorites(title = R.string.favorites)
}

@Composable
fun FoodComaBottomBar(
    navigateCategories: () -> Unit,
    navigateAreas: () -> Unit,
    navigateIngredients: () -> Unit
){
    BottomAppBar {
        BottomBarItem(
            label = R.string.categories,
            onClick = navigateCategories,
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            label = R.string.areas,
            onClick = navigateAreas,
            modifier = Modifier.weight(1f)
        )
        BottomBarItem(
            label = R.string.ingredients,
            onClick = navigateIngredients,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarItem(
    @StringRes label: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(2.dp)
            .fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
                .background(BottomBarOrange)
        ) {
            Text(stringResource(label))
        }
    }
}

@Composable
fun FoodComaApp(
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
){
    val foodComaViewModel: FoodComaViewModel = viewModel(factory = FoodComaViewModel.Factory)

    Scaffold(
        bottomBar = {
            FoodComaBottomBar(
                navigateCategories = { navController.navigate(MovieDBScreen.Categories.name) },     //TODO fix correct popping of stack
                navigateAreas = { navController.navigate(MovieDBScreen.Areas.name) },
                navigateIngredients = { navController.navigate(MovieDBScreen.Ingredients.name) }
            )
        }
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
            composable(route = MovieDBScreen.Areas.name) {
                AreaListScreen(
                    areaListUiState = foodComaViewModel.areaListUiState,
                    onAreaClick = { area ->
                        foodComaViewModel.setSelectedArea(area)
                        navController.navigate(MovieDBScreen.AreaDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.Ingredients.name) {
                IngredientListScreen(
                    ingredientListUiState = foodComaViewModel.ingredientListUiState,
                    onIngredientClick = { ingredient ->
                        foodComaViewModel.setSelectedIngredient(ingredient)
                        navController.navigate(MovieDBScreen.IngredientDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.CategoryDetail.name) {
                CategoryDetailScreen(
                    viewModel = foodComaViewModel,
                    onRecipeClick = { recipeID ->
                        foodComaViewModel.setSelectedRecipe(recipeID)
                        navController.navigate(MovieDBScreen.RecipeDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.AreaDetail.name) {
                AreaDetailScreen(
                    viewModel = foodComaViewModel,
                    onRecipeClick = { recipeID ->
                        foodComaViewModel.setSelectedRecipe(recipeID)
                        navController.navigate(MovieDBScreen.RecipeDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.IngredientDetail.name) {
                IngredientDetailScreen(
                    viewModel = foodComaViewModel,
                    onRecipeClick = { recipeID ->
                        foodComaViewModel.setSelectedRecipe(recipeID)
                        navController.navigate(MovieDBScreen.RecipeDetail.name)
                    }
                )
            }
            composable(route = MovieDBScreen.RecipeDetail.name) {
                RecipeDetailScreen(
                    viewModel = foodComaViewModel
                )
            }
        }
    }
}

