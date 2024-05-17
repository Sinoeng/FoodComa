package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodcoma.model.Ingredient
import com.example.foodcoma.viewmodel.IngredientListUiState


@Composable
fun IngredientListScreen(
    ingredientListUiState: IngredientListUiState,
    onIngredientClick: (Ingredient) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
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
        modifier =  modifier
            .height(64.dp)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(ingredient.strIngredient)
        }
    }
}