package com.example.foodcoma.ui.screens

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodcoma.viewmodel.FoodComaViewModel


@Composable
fun FavoritesScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    viewModel.getFavoriteRecipes()
    RecipeListScreen(
        viewModel = viewModel,
        onRecipeClick = onRecipeClick,
        windowSize = windowSize,
        modifier = modifier
    )
}