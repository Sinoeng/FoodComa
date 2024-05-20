package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedCategoryUiState


@Composable
fun CategoryDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedCategoryUiState = viewModel.selectedCategoryUiState
    when(selectedCategoryUiState) {
        is SelectedCategoryUiState.Success -> {
            viewModel.getRecipeListByCategory(selectedCategoryUiState.category.strCategory)
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = onRecipeClick,
                windowSize = windowSize,
                modifier = modifier
            )
        }
        SelectedCategoryUiState.Loading -> {
            Text("Loading category")
        }
        SelectedCategoryUiState.Error -> {
            Text("Error loading category")
        }
    }
}