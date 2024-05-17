package com.example.foodcoma.database

import com.example.foodcoma.model.AreaResponse
import com.example.foodcoma.network.FoodComaApiService

interface AreaRepository {
    suspend fun getAreas() : AreaResponse
}

class NetworkAreaRepository(
    private val apiService: FoodComaApiService
) : AreaRepository {
    override suspend fun getAreas(): AreaResponse {
        return apiService.getAreas()
    }

}