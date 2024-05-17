package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedRecipeUiState

@Composable
fun RecipeDetailScreen(
    viewModel: FoodComaViewModel,
    modifier: Modifier = Modifier
) {
    val selectedRecipeUiState = viewModel.selectedRecipeUiState
    when (selectedRecipeUiState) {
        is SelectedRecipeUiState.Success -> {
            RecipeScreen(
                recipe = selectedRecipeUiState.recipe,
                modifier = modifier
            )
        }
        SelectedRecipeUiState.Loading -> {
            Text(text = "Loading recipe")
        }
        SelectedRecipeUiState.Error -> {
            Text(text = "Error loading recipe")
        }
    }
}


@Composable
private fun RecipeScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Box {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = recipe.strMeal,
                )
            }
            Text("Remember to make this screen pretty with and stuff", fontWeight = FontWeight.Bold)
            Text(recipe.strInstructions)
        }
    }
}