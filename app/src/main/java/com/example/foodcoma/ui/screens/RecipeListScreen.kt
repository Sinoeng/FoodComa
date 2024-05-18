package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.model.RecipeShort
import com.example.foodcoma.ui.theme.FoodComaTheme
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.RecipeListUiState
import org.w3c.dom.Text


@Composable
fun RecipeListScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val rows = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            2
        }
        WindowWidthSizeClass.Medium -> {
            3
        }
        WindowWidthSizeClass.Expanded -> {
            4
        }
        else -> {
            2
        }
    }
    RecipeScreen(
        viewModel = viewModel,
        onRecipeClick = onRecipeClick,
        rows = rows,
        modifier = modifier
    )
}

@Composable
private fun RecipeScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    rows: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(rows),
        modifier = modifier
    ) {
        val recipeListUiState = viewModel.recipeListUiState

        when(recipeListUiState) {
            is RecipeListUiState.Success -> {
                items(recipeListUiState.recipeList) { recipe ->
                    RecipeItemCard(
                        recipe = recipe,
                        onRecipeClick = onRecipeClick,
                        isCompact = rows == 2
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
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeItemCard(
    recipe: RecipeShort,
    onRecipeClick: (String) -> Unit,
    isCompact: Boolean,
    modifier: Modifier = Modifier
) {
//    val textSize = when(isCompact) {
//        true -> {
//            TextUnit(value = 16f, type = TextUnitType.Sp)
//        }
//        false -> {
//            TextUnit(value = 24f, type = TextUnitType.Sp)
//        }
//    }
    Card(
        onClick = {
            onRecipeClick(recipe.idMeal)
        },
        modifier = modifier
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = recipe.strMeal,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                )
            }
            Text(
                text = recipe.strMeal,
//                fontSize = textSize,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
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