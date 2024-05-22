package com.example.foodcoma.utils

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.example.foodcoma.FoodComaScreen


fun clearBackStack(navHostController: NavHostController) {

    while(true) {
        if(!navHostController.popBackStack()) {
            break
        }
    }
//    navHostController.clearBackStack(FoodComaScreen.Category.name)
//    navHostController.clearBackStack(FoodComaScreen.Area.name)
//    navHostController.clearBackStack(FoodComaScreen.Ingredient.name)
//    navHostController.clearBackStack(FoodComaScreen.Favorites.name)
}