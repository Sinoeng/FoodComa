package com.example.foodcoma.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.AsyncImage
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.ui.theme.CardContainerColor
import com.example.foodcoma.ui.theme.CardContentColor
import com.example.foodcoma.ui.theme.CardDisabledContainerColor
import com.example.foodcoma.ui.theme.CardDisabledContentColor
import com.example.foodcoma.ui.theme.EvenIngredientColor
import com.example.foodcoma.ui.theme.OddIngredientColor
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedRecipeUiState

@Composable
fun RecipeDetailScreen(
    viewModel: FoodComaViewModel,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedRecipeUiState = viewModel.selectedRecipeUiState
    when (selectedRecipeUiState) {
        is SelectedRecipeUiState.Success -> {
            RecipeScreen(
                recipe = selectedRecipeUiState.recipe,
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
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            CompactScreen(
                recipe = recipe,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Medium -> {
            MediumScreen(
                recipe = recipe,
                modifier = modifier
            )
        }
        WindowWidthSizeClass.Expanded -> {
            ExpandedScreen(
                recipe = recipe,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun InstructionsColumn(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0x9CFFFFFF))
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = recipe.strInstructions.replace("\n", "\n\n"),
            lineHeight = 1.25.em,
            color = CardContentColor,
            modifier = Modifier
        )

    }
}

@Composable
private fun IngredientList(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    val ingredients = listOf(
        recipe.strIngredient1,
        recipe.strIngredient2,
        recipe.strIngredient3,
        recipe.strIngredient4,
        recipe.strIngredient5,
        recipe.strIngredient6,
        recipe.strIngredient7,
        recipe.strIngredient8,
        recipe.strIngredient9,
        recipe.strIngredient10,
        recipe.strIngredient11,
        recipe.strIngredient12,
        recipe.strIngredient13,
        recipe.strIngredient14,
        recipe.strIngredient15,
        recipe.strIngredient16,
        recipe.strIngredient17,
        recipe.strIngredient18,
        recipe.strIngredient19,
        recipe.strIngredient20
    )
    val amounts = listOf(
        recipe.strMeasure1,
        recipe.strMeasure2,
        recipe.strMeasure3,
        recipe.strMeasure4,
        recipe.strMeasure5,
        recipe.strMeasure6,
        recipe.strMeasure7,
        recipe.strMeasure8,
        recipe.strMeasure9,
        recipe.strMeasure10,
        recipe.strMeasure11,
        recipe.strMeasure12,
        recipe.strMeasure13,
        recipe.strMeasure14,
        recipe.strMeasure15,
        recipe.strMeasure16,
        recipe.strMeasure17,
        recipe.strMeasure18,
        recipe.strMeasure19,
        recipe.strMeasure20
    )

    var odd = true
    Column(
        modifier = modifier
    ) {
        ingredients.forEachIndexed { index, ingredient ->
            if (ingredient.isNullOrBlank()) {
                return@forEachIndexed
            }
            Row(
                modifier = Modifier
                    .background(if (odd) OddIngredientColor else EvenIngredientColor)
            ) {
                Text(
                    text = ingredient,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = amounts[index].orEmpty(),
                    modifier = Modifier
                        .weight(1f)
                )
            }
            odd = !odd
        }
    }
}


@Composable
private fun CompactScreen(
    recipe: Recipe,
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
        LazyColumn(
            modifier = Modifier
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .height(400.dp)
                )
                IngredientList(
                    recipe = recipe,
                    modifier = Modifier
                )

                InstructionsColumn(
                    recipe = recipe,
                    modifier = Modifier
                )
            }
        }
    }
}


@Composable
private fun MediumScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(ScrollState(0))
    ) {
        AsyncImage(
            model = recipe.strMealThumb,
            contentDescription = recipe.strMeal,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IngredientList(
                recipe = recipe,
                modifier = Modifier
                    .weight(2f)
            )
            Spacer(modifier = Modifier.weight(0.05f))
            InstructionsColumn(
                recipe = recipe,
                modifier = Modifier
                    .weight(2f)
            )
        }
    }
}


@Composable
private fun ExpandedScreen(
    recipe: Recipe,
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
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
        ) {
            item {
                IngredientList(
                    recipe = recipe,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                InstructionsColumn(
                    recipe = recipe,
                    modifier = Modifier
                )
            }
        }
    }
}

