package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.model.RecipeShort
import com.example.foodcoma.ui.theme.FoodComaTheme
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.RecipeListUiState


@Composable
fun RecipeListScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier
    ) {
        val recipeListUiState = viewModel.recipeListUiState

        when(recipeListUiState) {
            is RecipeListUiState.Success -> {
                items(recipeListUiState.recipeList) { recipe ->
                    RecipeItemCard(
                        recipe = recipe,
                        onRecipeClick = onRecipeClick
                    )
                }
            }
            RecipeListUiState.Loading -> {
                item {
                    Text("Loading recipes")
                }
            }
            RecipeListUiState.Error -> {
                item {
                    Text("Error loading recipes")
                }
            }

            else -> {}
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItemCard(
    recipe: RecipeShort,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text("test")

    Card(
        onClick = {
            onRecipeClick(recipe.idMeal)
        },
        modifier = modifier

    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Box {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = recipe.strMeal,
                    contentScale = ContentScale.Crop,
                )
            }
            Text(text = recipe.strMeal)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RecipeItemCardPreview() {
//    FoodComaTheme {
//        val recipe = Recipe(
//            strMeal = "Baked salmon with fennel & tomatoes",
//            strMealThumb = "https://www.themealdb.com/images/media/meals/1548772327.jpg"
//        )
//        RecipeItemCard(
//            recipe = recipe,
//            onRecipeClick = {}
//        )
//    }
//}