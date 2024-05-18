package com.example.foodcoma.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.example.foodcoma.model.Area
import com.example.foodcoma.viewmodel.AreaListUiState


@Composable
fun AreaListScreen(
    areaListUiState: AreaListUiState,
    onAreaClick: (Area) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        when(areaListUiState) {
            is AreaListUiState.Success -> {
                items(areaListUiState.areas) { area ->
                    AreaItemCard(
                        area = area,
                        onAreaClick = onAreaClick
                    )
                }
            }

            AreaListUiState.Loading -> {
                item {
                    Text("Loading areas")
                }
            }
            AreaListUiState.Error -> {
                item {
                    Text("Error loading areas")        // TODO: extract
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaItemCard(
    area: Area,
    onAreaClick: (Area) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onAreaClick(area) },
        modifier = modifier
            .padding(4.dp)
            .height(64.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(area.strArea)
        }
    }
}