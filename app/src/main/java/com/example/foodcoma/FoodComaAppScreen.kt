package com.example.foodcoma

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.example.foodcoma.ui.components.SearchScreen
import com.example.foodcoma.ui.components.FoodComaBottomBar
import com.example.foodcoma.ui.components.FoodComaNavigationRail
import com.example.foodcoma.ui.components.FoodComaTopBar
import com.example.foodcoma.utils.Constants.PULL_TO_REFRESH_THRESHOLD
import com.example.foodcoma.viewmodel.FoodComaViewModel
import com.example.foodcoma.workers.ScheduledRefreshWorker

enum class FoodComaScreen(@StringRes val title: Int){
    Category(title = R.string.category),
    Area(title = R.string.area),
    Ingredient(title = R.string.ingredient),
    Search(title = R.string.search),
    Favorites(title = R.string.favorites),
    CategoryDetail(title = R.string.category_detail),
    AreaDetail(title = R.string.area_detail),
    IngredientDetail(title = R.string.ingredient_detail),
    RecipeDetail(title = R.string.recipe_detail)
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
                    navController.navigate(FoodComaScreen.Category.name)
                },
                navigateAreas = {
                    navController.navigate(FoodComaScreen.Area.name)
                },
                navigateIngredients = {
                    navController.navigate(FoodComaScreen.Ingredient.name)
                },
                navigateSearch = {
                    navController.navigate(FoodComaScreen.Search.name)
                },
                navigateFavorites = {
                    navController.navigate(FoodComaScreen.Favorites.name)
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
                    navController.navigate(FoodComaScreen.Category.name)
                },
                navigateAreas = {
                    navController.navigate(FoodComaScreen.Area.name)
                },
                navigateIngredients = {
                    navController.navigate(FoodComaScreen.Ingredient.name)
                },
                navigateSearch = {
                     navController.navigate(FoodComaScreen.Search.name)
                },
                navigateFavorites = {
                    navController.navigate(FoodComaScreen.Favorites.name)
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
                    viewModel = foodComaViewModel,
                    modifier = Modifier
                )
            },
            bottomBar = navBar
        ) { innerPadding ->
            val state = rememberPullToRefreshState(PULL_TO_REFRESH_THRESHOLD.dp)
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
                NavigationManager(
                    navController = navController,
                    foodComaViewModel = foodComaViewModel,
                    windowSize = windowSize,
                    innerPadding = innerPadding
                )
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

@Composable
private fun NavigationManager(
    navController: NavHostController,
    foodComaViewModel: FoodComaViewModel,
    windowSize: WindowWidthSizeClass,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FoodComaScreen.Category.name,
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = FoodComaScreen.Category.name) {
            CategoryListScreen(
                categoryUiState = foodComaViewModel.categoryListUiState,
                onCategoryClick = { category ->
                    foodComaViewModel.setSelectedCategory(category)
                    navController.navigate(FoodComaScreen.CategoryDetail.name)
                },
                windowSize = windowSize
            )
        }
        composable(route = FoodComaScreen.Area.name) {
            AreaListScreen(
                areaListUiState = foodComaViewModel.areaListUiState,
                onAreaClick = { area ->
                    foodComaViewModel.setSelectedArea(area)
                    navController.navigate(FoodComaScreen.AreaDetail.name)
                },
                windowSize = windowSize
            )
        }
        composable(route = FoodComaScreen.Ingredient.name) {
            IngredientListScreen(
                ingredientListUiState = foodComaViewModel.ingredientListUiState,
                onIngredientClick = { ingredient ->
                    foodComaViewModel.setSelectedIngredient(ingredient)
                    navController.navigate(FoodComaScreen.IngredientDetail.name)
                },
                windowSize = windowSize
            )
        }
        composable(route = FoodComaScreen.Search.name) {
            SearchScreen(
                viewModel = foodComaViewModel,
                onRecipeClick = { recipeID ->
                    foodComaViewModel.setSelectedRecipe(recipeID)
                    navController.navigate(FoodComaScreen.RecipeDetail.name)
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
}

