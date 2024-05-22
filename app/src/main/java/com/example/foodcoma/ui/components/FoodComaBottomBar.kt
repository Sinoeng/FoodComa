package com.example.foodcoma.ui.components

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.R
import com.example.foodcoma.ui.theme.BottomBarDisabledColor
import com.example.foodcoma.ui.theme.BottomBarSelectedColor
import com.example.foodcoma.ui.theme.BottomBarUnselectedColor

@Composable
fun FoodComaBottomBar(
    currentRoute: String?,
    navigateCategories: () -> Unit,
    navigateAreas: () -> Unit,
    navigateIngredients: () -> Unit,
    navigateSearch: () -> Unit,
    navigateFavorites: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        Log.d(com.example.foodcoma.TAG, "navController: $currentRoute")
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
            selected = currentRoute == FoodComaScreen.Category.name,
            onClick = navigateCategories,
            label = { Text(stringResource(R.string.category)) },
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
            selected = currentRoute == FoodComaScreen.Area.name,
            onClick = navigateAreas,
            label = { Text(stringResource(R.string.area)) },
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
            selected = currentRoute == FoodComaScreen.Ingredient.name,
            onClick = navigateIngredients,
            label = { Text(stringResource(R.string.ingredient)) },
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
            selected = currentRoute == FoodComaScreen.Search.name,
            onClick = navigateSearch,
            label = { Text(stringResource(R.string.search)) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            colors = navigationBarItemColors
        )
        NavigationBarItem(
            selected = currentRoute == FoodComaScreen.Favorites.name,
            onClick = navigateFavorites,
            label = { Text(stringResource(R.string.favorites)) },
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