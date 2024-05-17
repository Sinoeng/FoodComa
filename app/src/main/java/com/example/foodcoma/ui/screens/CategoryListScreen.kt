package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodcoma.model.Category
import com.example.foodcoma.viewmodel.CategoryListUiState

@Composable
fun CategoryListScreen(
    categoryUiState: CategoryListUiState,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        when(categoryUiState) {
            is CategoryListUiState.Success -> {
                items(categoryUiState.categoryList) { category ->
                    CategoryItemCard(
                        category = category,
                        onCategoryClick = onCategoryClick
                    )
                }
            }

            CategoryListUiState.Loading -> {
                item {
                    Text("Loading categories")
                }
            }
            CategoryListUiState.Error -> {
                item {
                    Text("Error loading categories")        // TODO: extract
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItemCard(
    category: Category,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            onCategoryClick(category)
        },
        modifier = modifier
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                AsyncImage(
                    model = category.strCategoryThumb,
                    contentDescription = category.strCategory,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(category.strCategory)
        }

    }
}