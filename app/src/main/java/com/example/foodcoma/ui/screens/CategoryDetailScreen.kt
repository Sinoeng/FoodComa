package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.utils.Constants.PULL_TO_REFRESH_THRESHOLD
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedCategoryUiState
import com.example.foodcoma.viewmodel.SelectedRecipeIDUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedCategoryUiState = viewModel.selectedCategoryUiState
    when(selectedCategoryUiState) {
        is SelectedCategoryUiState.Success -> {
            viewModel.getRecipeListByCategory(selectedCategoryUiState.category.strCategory)
            val state = rememberPullToRefreshState(PULL_TO_REFRESH_THRESHOLD.dp)
            if (state.isRefreshing) {
                LaunchedEffect(true) {
                    viewModel.getRecipeListByCategory(selectedCategoryUiState.category.strCategory)
                    state.endRefresh()
                }
            }
            Box(
                modifier = modifier
                    .nestedScroll(state.nestedScrollConnection)
                    .fillMaxSize()
            ) {
                RecipeListScreen(
                    viewModel = viewModel,
                    onRecipeClick = onRecipeClick,
                    windowSize = windowSize,
                    modifier = Modifier
                )
                PullToRefreshContainer(
                    state = state,
                    indicator = {
                        Indicator(state)
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        }
        SelectedCategoryUiState.Loading -> {
            Text("Loading category")
        }
        SelectedCategoryUiState.Error -> {
            Text("Error loading category")
        }
    }
}