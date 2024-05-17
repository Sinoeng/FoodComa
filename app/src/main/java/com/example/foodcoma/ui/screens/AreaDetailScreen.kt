package com.example.foodcoma.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedAreaUiState
import com.example.foodcoma.viewmodel.SelectedCategoryUiState


@Composable
fun AreaDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedAreaUiState = viewModel.selectedAreaUiState
    when(selectedAreaUiState) {
        is SelectedAreaUiState.Success -> {
            viewModel.getRecipeListByArea(selectedAreaUiState.area)
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = onRecipeClick,
                modifier = modifier
            )
        }
        SelectedAreaUiState.Loading -> {
            Text("Loading area")
        }
        SelectedAreaUiState.Error -> {
            Text("Error loading area")
        }
    }
}