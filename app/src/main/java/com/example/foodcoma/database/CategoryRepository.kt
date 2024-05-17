package com.example.foodcoma.database

import com.example.foodcoma.model.Category
import com.example.foodcoma.model.CategoryResponse
import com.example.foodcoma.network.FoodComaApiService

interface CategoryRepository {
    suspend fun getCategories(): CategoryResponse
}

class NetworkCategoryRepository(
    private val apiService: FoodComaApiService
) : CategoryRepository {
    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategories()
    }

}