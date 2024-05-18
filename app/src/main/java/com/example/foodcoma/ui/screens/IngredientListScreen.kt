package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodcoma.model.Ingredient
import com.example.foodcoma.ui.theme.CardContainerColor
import com.example.foodcoma.ui.theme.CardContentColor
import com.example.foodcoma.ui.theme.CardDisabledContainerColor
import com.example.foodcoma.ui.theme.CardDisabledContentColor
import com.example.foodcoma.viewmodel.IngredientListUiState


@Composable
fun IngredientListScreen(
    ingredientListUiState: IngredientListUiState,
    onIngredientClick: (Ingredient) -> Unit,
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
    IngredientScreen(
        ingredientListUiState = ingredientListUiState,
        onIngredientClick = onIngredientClick,
        rows = rows,
        modifier = modifier
    )
}

@Composable
private fun IngredientScreen(
    ingredientListUiState: IngredientListUiState,
    onIngredientClick: (Ingredient) -> Unit,
    rows: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(rows), modifier = modifier) {
        when (ingredientListUiState) {
            is IngredientListUiState.Success -> {
                items(ingredientListUiState.ingredients) { ingredient ->
                    IngredientCard(
                        ingredient = ingredient,
                        onIngredientClick = onIngredientClick
                    )
                }
            }
            IngredientListUiState.Loading -> {
                item {
                    Text(text = "Loading ingredients")
                }
            }
            IngredientListUiState.Error -> {
                item {
                    Text(text = "Error loading ingredients")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientCard(
    ingredient: Ingredient,
    onIngredientClick : (Ingredient) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onIngredientClick(ingredient) },
        colors = CardColors(
            containerColor = CardContainerColor,
            contentColor = CardContentColor,
            disabledContainerColor = CardDisabledContainerColor,
            disabledContentColor = CardDisabledContentColor
        ),
        modifier =  modifier
            .height(64.dp)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(ingredient.strIngredient)
        }
    }
}