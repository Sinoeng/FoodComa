package com.example.foodcoma.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedRecipeUiState

@Composable
fun RecipeDetailScreen(
    viewModel: FoodComaViewModel,
    onFavoriteClick: (Boolean, String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedRecipeUiState = viewModel.selectedRecipeUiState
    when (selectedRecipeUiState) {
        is SelectedRecipeUiState.Success -> {
            RecipeScreen(
                recipe = selectedRecipeUiState.recipe,
                selectedRecipeUiState = selectedRecipeUiState,
                onFavoriteClick = onFavoriteClick,
                windowSize = windowSize,
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
    selectedRecipeUiState: SelectedRecipeUiState.Success,
    onFavoriteClick: (Boolean, String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactScreen(
                recipe = recipe,
                selectedRecipeUiState = selectedRecipeUiState,
                onFavoriteClick = onFavoriteClick,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumScreen(
                recipe = recipe,
                selectedRecipeUiState = selectedRecipeUiState,
                onFavoriteClick = onFavoriteClick,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            ExpandedScreen(
                recipe = recipe,
                selectedRecipeUiState = selectedRecipeUiState,
                onFavoriteClick = onFavoriteClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun InstructionsColumn(
    recipe: Recipe,
    selectedRecipeUiState: SelectedRecipeUiState.Success,
    onFavoriteClick: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(400.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.DarkGray)
            ) {
                Text("Favorite: ")
                Switch(
                    checked = selectedRecipeUiState.isFavorite,
                    onCheckedChange = {
                        onFavoriteClick(it, recipe.idMeal)
                    }
                )
            }
            Text(
                recipe.strInstructions,
                modifier = Modifier
                    .background(Color(0x80ffffff))
            )
            Spacer(modifier = Modifier.height(400.dp))
        }
    }
}


@Composable
private fun CompactScreen(
    recipe: Recipe,
    selectedRecipeUiState: SelectedRecipeUiState.Success,
    onFavoriteClick: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = recipe.strMealThumb,
            contentDescription = recipe.strMeal,
            modifier = Modifier
                .fillMaxWidth()
        )
        InstructionsColumn(
            recipe = recipe,
            selectedRecipeUiState = selectedRecipeUiState,
            onFavoriteClick = onFavoriteClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Composable
private fun MediumScreen(
    recipe: Recipe,
    selectedRecipeUiState: SelectedRecipeUiState.Success,
    onFavoriteClick: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO?
}


@Composable
private fun ExpandedScreen(
    recipe: Recipe,
    selectedRecipeUiState: SelectedRecipeUiState.Success,
    onFavoriteClick: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        Box {
            AsyncImage(
                model = recipe.strMealThumb,
                contentDescription = recipe.strMeal,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
        InstructionsColumn(
            recipe = recipe,
            selectedRecipeUiState = selectedRecipeUiState,
            onFavoriteClick = onFavoriteClick,
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}

