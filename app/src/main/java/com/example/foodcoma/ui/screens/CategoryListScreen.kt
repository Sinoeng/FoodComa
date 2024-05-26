package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodcoma.R
import com.example.foodcoma.model.Category
import com.example.foodcoma.ui.theme.CardContainerColor
import com.example.foodcoma.ui.theme.CardContentColor
import com.example.foodcoma.ui.theme.CardDisabledContainerColor
import com.example.foodcoma.ui.theme.CardDisabledContentColor
import com.example.foodcoma.ui.theme.Typography
import com.example.foodcoma.viewmodel.CategoryListUiState

@Composable
fun CategoryListScreen(
    categoryUiState: CategoryListUiState,
    onCategoryClick: (Category) -> Unit,
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
    CategoryScreen(
        categoryUiState = categoryUiState,
        onCategoryClick = onCategoryClick,
        rows = rows,
        modifier = modifier
    )
}

@Composable
private fun CategoryScreen(
    categoryUiState: CategoryListUiState,
    onCategoryClick: (Category) -> Unit,
    rows: Int,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(rows), modifier = modifier) {
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
                    Text(stringResource(R.string.loading_string, stringResource(R.string.categories)))
                }
            }
            CategoryListUiState.Error -> {
                item {
                    Text(stringResource(R.string.error_loading_string, stringResource(R.string.categories)))
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
        colors = CardColors(
            containerColor = CardContainerColor,
            contentColor = CardContentColor,
            disabledContainerColor = CardDisabledContainerColor,
            disabledContentColor = CardDisabledContentColor
        ),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(14f / 9f)
                )
            }
            Text(
                category.strCategory,
                style = Typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}