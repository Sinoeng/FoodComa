package com.example.foodcoma.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodcoma.R
import com.example.foodcoma.ui.screens.RecipeListScreen
import com.example.foodcoma.viewmodel.FoodComaViewModel

private const val TAG = "SearchScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: FoodComaViewModel,
    onRecipeClick: (String) -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    var searched by rememberSaveable { mutableStateOf(false) }
    Column {
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {
                Log.d(TAG, "onSearch: $it")
                viewModel.getRecipeListBySearch(query = query)
                searched = true
            },
            active = false,
            onActiveChange = {
                active = it
            },
            placeholder = { Text(stringResource(R.string.search)) },
            leadingIcon = { Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            ) },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        ) {

        }
        if (searched) {
            RecipeListScreen(
                viewModel = viewModel,
                onRecipeClick = onRecipeClick,
                windowSize = windowSize
            )
        }

    }
}

