package com.example.foodcoma.ui.screens

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedAreaUiState
import com.example.foodcoma.viewmodel.SelectedIngredientUiState


@Composable
fun IngredientDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedIngredientUiState = viewModel.selectedIngredientUiState
    when(selectedIngredientUiState) {
        is SelectedIngredientUiState.Success -> {
            viewModel.getRecipeListByIngredient(selectedIngredientUiState.ingredient)
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = onRecipeClick,
                windowSize = windowSize,
                modifier = modifier
            )
        }
        SelectedIngredientUiState.Loading -> {
            Text("Loading area")
        }
        SelectedIngredientUiState.Error -> {
            Text("Error loading area")
        }
    }
}