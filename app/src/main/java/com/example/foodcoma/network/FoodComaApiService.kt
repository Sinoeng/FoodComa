package com.example.foodcoma.network


import com.example.foodcoma.model.RecipeResponse
import com.example.foodcoma.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodComaApiService {
    @GET("categories")      // .php ?
    suspend fun getCategories(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    )

    @GET("movie/popular")
    suspend fun getRecipe(     // TODO: not sure how to get specific recepie
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): RecipeResponse

}
