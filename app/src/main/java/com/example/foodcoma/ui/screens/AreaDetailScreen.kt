package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.foodcoma.utils.Constants
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedAreaUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaDetailScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val selectedAreaUiState = viewModel.selectedAreaUiState
    when(selectedAreaUiState) {
        is SelectedAreaUiState.Success -> {
            viewModel.getRecipeListByArea(selectedAreaUiState.area.strArea)
            val state = rememberPullToRefreshState(Constants.PULL_TO_REFRESH_THRESHOLD.dp)
            if (state.isRefreshing) {
                LaunchedEffect(true) {
                    viewModel.getRecipeListByArea(selectedAreaUiState.area.strArea)
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
                    modifier = modifier
                )
                PullToRefreshContainer(
                    state = state,
                    indicator = {
                        PullToRefreshDefaults.Indicator(state)
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )
            }
        }
        SelectedAreaUiState.Loading -> {
            Text("Loading area")
        }
        SelectedAreaUiState.Error -> {
            Text("Error loading area")
        }
    }
}