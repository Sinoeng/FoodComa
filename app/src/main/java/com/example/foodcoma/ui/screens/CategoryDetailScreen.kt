package com.example.foodcoma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedCategoryUiState


@Composable
fun CategoryDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCategoryUiState = viewModel.selectedCategoryUiState
    when(selectedCategoryUiState) {
        is SelectedCategoryUiState.Success -> {
            viewModel.getRecipeListByCategory(selectedCategoryUiState.category)
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = onRecipeClick,
                modifier = modifier
            )
        }
        SelectedCategoryUiState.Loading -> TODO()
        SelectedCategoryUiState.Error -> TODO()
    }
}