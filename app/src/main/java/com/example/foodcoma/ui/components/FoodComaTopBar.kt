package com.example.foodcoma.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.ui.theme.TopBarColor
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedAreaUiState
import com.example.foodcoma.viewmodel.SelectedCategoryUiState
import com.example.foodcoma.viewmodel.SelectedIngredientUiState
import com.example.foodcoma.viewmodel.SelectedRecipeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodComaTopBar(             //TODO: move the topbar, etc, to a separate file
    currentRoute: String?,
    viewModel: FoodComaViewModel,
    modifier: Modifier = Modifier
) {
    var actions: @Composable RowScope.() -> Unit = {}
    actions = {
        var menuExpanded by remember { mutableStateOf(false) }
        IconButton(
            onClick = {
                menuExpanded = ! menuExpanded
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Clear database"
            )
        }
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = {
                menuExpanded = false
            }) {
            DropdownMenuItem(
                text = { Text("Clear database") },
                onClick = {
                    viewModel.clearDatabase()
                    menuExpanded = false
                }
            )
        }
    }
    val title = when (currentRoute) {
        FoodComaScreen.Category.name -> stringResource(id = FoodComaScreen.Category.title)
        FoodComaScreen.Area.name -> stringResource(id = FoodComaScreen.Area.title)
        FoodComaScreen.Ingredient.name -> stringResource(id = FoodComaScreen.Ingredient.title)
        FoodComaScreen.Search.name -> stringResource(id = FoodComaScreen.Search.title)
        FoodComaScreen.Favorites.name -> stringResource(id = FoodComaScreen.Favorites.title)
        FoodComaScreen.CategoryDetail.name -> {
            val state = viewModel.selectedCategoryUiState
            if (state is SelectedCategoryUiState.Success) {
                state.category.strCategory
            } else {
                ""
            }
        }
        FoodComaScreen.AreaDetail.name -> {
            val state = viewModel.selectedAreaUiState
            if (state is SelectedAreaUiState.Success) {
                state.area.strArea
            } else {
                ""
            }
        }
        FoodComaScreen.IngredientDetail.name -> {
            val state = viewModel.selectedIngredientUiState
            if (state is SelectedIngredientUiState.Success) {
                state.ingredient.strIngredient
            } else {
                ""
            }
        }
        FoodComaScreen.RecipeDetail.name -> {
            val state = viewModel.selectedRecipeUiState
            if (state is SelectedRecipeUiState.Success) {
                actions = @Composable {
                    FavoriteSwitch(
                        checked = state.isFavorite,
                        onFavoriteClick = { favorite, recipe ->
                            if (favorite) {
                                viewModel.setFavoriteRecipe(recipe)
                            } else {
                                viewModel.unsetFavoriteRecipe(recipe.idMeal)
                            }
                        },
                        recipe = state.recipe
                    )
                }
                state.recipe.strMeal
            } else {
                ""
            }
        }
        else -> "Err"
    }
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarColors(
            containerColor = TopBarColor,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Blue,        // TODO: decide these colors
            actionIconContentColor = Color.White,
            scrolledContainerColor = Color.Gray
        ),
        actions = actions,
        modifier = modifier
    )
}