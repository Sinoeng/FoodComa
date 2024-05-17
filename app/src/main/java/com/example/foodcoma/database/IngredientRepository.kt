package com.example.foodcoma.database

import com.example.foodcoma.model.Ingredient
import com.example.foodcoma.model.IngredientResponse
import com.example.foodcoma.network.FoodComaApiService

interface IngredientRepository {
    suspend fun getIngredients(): IngredientResponse
}

class NetworkIngredientRepository(
    private val apiService: FoodComaApiService
) : IngredientRepository {
    override suspend fun getIngredients(): IngredientResponse {
        return apiService.getIngredients()
    }
}