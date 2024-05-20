package com.example.foodcoma

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodcoma.ui.screens.AreaDetailScreen
import com.example.foodcoma.ui.screens.AreaListScreen
import com.example.foodcoma.ui.screens.CategoryDetailScreen
import com.example.foodcoma.ui.screens.CategoryListScreen
import com.example.foodcoma.ui.screens.FavoritesScreen
import com.example.foodcoma.ui.screens.IngredientDetailScreen
import com.example.foodcoma.ui.screens.IngredientListScreen
import com.example.foodcoma.ui.screens.RecipeDetailScreen
import com.example.foodcoma.ui.screens.RecipeListScreen
import com.example.foodcoma.ui.screens.components.FavoriteSwitch
import com.example.foodcoma.ui.theme.BottomBarDisabledColor
import com.example.foodcoma.ui.theme.BottomBarSelectedColor
import com.example.foodcoma.ui.theme.BottomBarUnselectedColor
import com.example.foodcoma.ui.theme.TopBarColor
import com.example.foodcoma.utils.Constants
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.viewmodel.SelectedAreaUiState
import com.example.foodcoma.viewmodel.SelectedCategoryUiState
import com.example.foodcoma.viewmodel.SelectedIngredientUiState
import com.example.foodcoma.viewmodel.SelectedRecipeUiState
import com.example.foodcoma.workers.ScheduledRefreshWorker

const val TAG = "FoodComaAppScreen"

enum class FoodComaScreen(@StringRes val title: Int){
    Categories(title = R.string.categories),
    Areas(title = R.string.areas),
    Ingredients(title = R.string.ingredients),
    Favorites(title = R.string.favorites),
    CategoryDetail(title = R.string.category_detail),
    AreaDetail(title = R.string.area_detail),
    IngredientDetail(title = R.string.ingredient_detail),
    RecipeDetail(title = R.string.recipe_detail)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodComaTopBar(             //TODO: move the topbar, etc, to a separate file
    currentRoute: String?,
    viewModel: FoodComaViewModel,
    modifier: Modifier = Modifier
) {
    var actions: @Composable RowScope.() -> Unit = {}
    val title = when (currentRoute) {
        FoodComaScreen.Categories.name -> stringResource(id = FoodComaScreen.Categories.title)
        FoodComaScreen.Areas.name -> stringResource(id = FoodComaScreen.Areas.title)
        FoodComaScreen.Ingredients.name -> stringResource(id = FoodComaScreen.Ingredients.title)
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

@Composable
fun FoodComaBottomBar(
    currentRoute: String?,
    navigateCategories: () -> Unit,
    navigateAreas: () -> Unit,
    navigateIngredients: () -> Unit,
    navigateFavorites: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        Log.d(TAG, "navController: $currentRoute")
        val navigationBarItemColors = NavigationBarItemColors(
            selectedIconColor = Color.White,        // TODO: check how this looks with the icons
            selectedTextColor = BottomBarSelectedColor,
            unselectedIconColor = BottomBarUnselectedColor,
            unselectedTextColor = BottomBarUnselectedColor,
            disabledIconColor = BottomBarDisabledColor,
            disabledTextColor = BottomBarDisabledColor,
            selectedIndicatorColor = BottomBarSelectedColor
        )
        NavigationBarItem(
            selected = currentRoute == FoodComaScreen.Categories.name,
            onClick = navigateCategories,
            label = { Text(text = "Categories") },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = currentRoute == FoodComaScreen.Areas.name,
            onClick = navigateAreas,
            label = { Text(text = "Areas") },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = currentRoute == FoodComaScreen.Ingredients.name,
            onClick = navigateIngredients,
            label = { Text(text = "Ingredients") },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Face,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = currentRoute == FoodComaScreen.Favorites.name,
            onClick = navigateFavorites,
            label = { Text(text = "Favorites") },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationBarItemColors
        )
    }
}

@Composable
fun FoodComaNavigationRail(
    currentRoute: String?,
    navigateCategories: () -> Unit,
    navigateAreas: () -> Unit,
    navigateIngredients: () -> Unit,
    navigateFavorites: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationRailItemColors = NavigationRailItemColors(
        selectedIconColor = Color.White,        // TODO: check how this looks with the icons
        selectedTextColor = BottomBarSelectedColor,
        unselectedIconColor = BottomBarUnselectedColor,
        unselectedTextColor = BottomBarUnselectedColor,
        disabledIconColor = BottomBarDisabledColor,
        disabledTextColor = BottomBarDisabledColor,
        selectedIndicatorColor = BottomBarSelectedColor
    )
    NavigationRail(
        modifier = modifier
    ) {
        NavigationRailItem(
            selected = currentRoute == FoodComaScreen.Categories.name,
            onClick = navigateCategories,
            label = { Text(text = stringResource(R.string.categories)) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationRailItemColors
        )
        NavigationRailItem(
            selected = currentRoute == FoodComaScreen.Areas.name,
            onClick = navigateAreas,
            label = { Text(text = stringResource(R.string.areas)) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationRailItemColors
        )
        NavigationRailItem(
            selected = currentRoute == FoodComaScreen.Ingredients.name,
            onClick = navigateIngredients,
            label = { Text(text = stringResource(R.string.ingredients)) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Face,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationRailItemColors
        )
        NavigationRailItem(
            selected = currentRoute == FoodComaScreen.Favorites.name,
            onClick = navigateFavorites,
            label = { Text(text = stringResource(R.string.favorites)) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationRailItemColors
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodComaApp(
    windowSize: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
){
    val foodComaViewModel: FoodComaViewModel = viewModel(factory = FoodComaViewModel.Factory)
    ScheduledRefreshWorker.viewmodel = foodComaViewModel

    // decides navigation style
    val navRail: @Composable () -> Unit
    val navBar: @Composable () -> Unit
    if (windowSize == WindowWidthSizeClass.Expanded) {
        navRail = {
            FoodComaNavigationRail(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                navigateCategories = {
                    navController.navigate(FoodComaScreen.Categories.name) {
                        popUpTo(FoodComaScreen.Categories.name) {
                            inclusive = true
                        }
                    }
                },
                navigateAreas = {
                    navController.navigate(FoodComaScreen.Areas.name) {
                        popUpTo(FoodComaScreen.Areas.name) {
                            inclusive = true
                        }
                    }
                },
                navigateIngredients = {
                    navController.navigate(FoodComaScreen.Ingredients.name) {
                        popUpTo(FoodComaScreen.Ingredients.name) {
                            inclusive = true
                        }
                    }
                },
                navigateFavorites = {
                    navController.navigate(FoodComaScreen.Favorites.name) {
                        popUpTo(FoodComaScreen.Favorites.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        navBar = {}
    } else {
        navRail = {}
        navBar = {
            FoodComaBottomBar(
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                navigateCategories = {
                    navController.navigate(FoodComaScreen.Categories.name) {
                        popUpTo(FoodComaScreen.Categories.name) {
                            inclusive = true
                        }
                    }
                },
                navigateAreas = {
                    navController.navigate(FoodComaScreen.Areas.name) {
                        popUpTo(FoodComaScreen.Areas.name) {
                            inclusive = true
                        }
                    }
                },
                navigateIngredients = {
                    navController.navigate(FoodComaScreen.Ingredients.name) {
                        popUpTo(FoodComaScreen.Ingredients.name) {
                            inclusive = true
                        }
                    }
                },
                navigateFavorites = {
                    navController.navigate(FoodComaScreen.Favorites.name) {
                        popUpTo(FoodComaScreen.Favorites.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }

    Row {
        navRail()
        Scaffold(
            topBar = {
                FoodComaTopBar(
                    currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
                    viewModel = foodComaViewModel
                )
            },
            bottomBar = navBar
        ) { innerPadding ->
            val state = rememberPullToRefreshState(Constants.PULL_TO_REFRESH_THRESHOLD.dp)
            if (state.isRefreshing) {
                LaunchedEffect(true) {
                    foodComaViewModel.getStarter()
                    state.endRefresh()
                }
            }
            Box(
                modifier = modifier
                    .nestedScroll(state.nestedScrollConnection)
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = FoodComaScreen.Categories.name,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    composable(route = FoodComaScreen.Categories.name) {
                        CategoryListScreen(
                            categoryUiState = foodComaViewModel.categoryListUiState,
                            onCategoryClick = { category ->
                                foodComaViewModel.setSelectedCategory(category)
                                navController.navigate(FoodComaScreen.CategoryDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.Areas.name) {
                        AreaListScreen(
                            areaListUiState = foodComaViewModel.areaListUiState,
                            onAreaClick = { area ->
                                foodComaViewModel.setSelectedArea(area)
                                navController.navigate(FoodComaScreen.AreaDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.Ingredients.name) {
                        IngredientListScreen(
                            ingredientListUiState = foodComaViewModel.ingredientListUiState,
                            onIngredientClick = { ingredient ->
                                foodComaViewModel.setSelectedIngredient(ingredient)
                                navController.navigate(FoodComaScreen.IngredientDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.Favorites.name) {
                        FavoritesScreen(
                            viewModel = foodComaViewModel,
                            onRecipeClick = { recipeID ->
                                foodComaViewModel.setSelectedRecipe(recipeID)
                                navController.navigate(FoodComaScreen.RecipeDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.CategoryDetail.name) {
                        CategoryDetailScreen(
                            viewModel = foodComaViewModel,
                            onRecipeClick = { recipeID ->
                                foodComaViewModel.setSelectedRecipe(recipeID)
                                navController.navigate(FoodComaScreen.RecipeDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.AreaDetail.name) {
                        AreaDetailScreen(
                            viewModel = foodComaViewModel,
                            onRecipeClick = { recipeID ->
                                foodComaViewModel.setSelectedRecipe(recipeID)
                                navController.navigate(FoodComaScreen.RecipeDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.IngredientDetail.name) {
                        IngredientDetailScreen(
                            viewModel = foodComaViewModel,
                            onRecipeClick = { recipeID ->
                                foodComaViewModel.setSelectedRecipe(recipeID)
                                navController.navigate(FoodComaScreen.RecipeDetail.name)
                            },
                            windowSize = windowSize
                        )
                    }
                    composable(route = FoodComaScreen.RecipeDetail.name) {
                        RecipeDetailScreen(
                            viewModel = foodComaViewModel,
                            windowSize = windowSize
                        )
                    }
                }
                PullToRefreshContainer(
                    state = state,
                    indicator = {
                        PullToRefreshDefaults.Indicator(state)
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 50.dp)
                )
            }
        }
    }
}

