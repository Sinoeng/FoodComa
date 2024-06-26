package com.example.foodcoma.network


import com.example.foodcoma.model.AreaResponse
import com.example.foodcoma.model.CategoryResponse
import com.example.foodcoma.model.IngredientResponse
import com.example.foodcoma.model.RecipeResponse
import com.example.foodcoma.model.RecipeShortResponse
import com.example.foodcoma.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodComaApiService {
    @GET("{api_key}/categories.php")
    suspend fun getCategories(
        @Path("api_key")
        apiKey: String = Constants.API_KEY
    ): CategoryResponse

    @GET("{api_key}/lookup.php")
    suspend fun getRecipeById(
        @Path("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("i")
        id: String
    ): RecipeResponse

    @GET("{api_key}/search.php")
    suspend fun getRecipeBySearch(
        @Path("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("s")
        name: String
    ): RecipeShortResponse

    @GET("{api_key}/filter.php?")
    suspend fun getRecipeByIngredient(
        @Path("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("i")
        ingredient: String
    ): RecipeShortResponse

    //@GET("{api_key}/filter.php?c={category}")
    @GET("{api_key}/filter.php")
    suspend fun getRecipeByCategory(
        @Path("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("c")
        category: String
    ): RecipeShortResponse

    @GET("{api_key}/filter.php")
    suspend fun getRecipeByArea(
        @Path("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("a")
        area: String
    ): RecipeShortResponse

    @GET("{api_key}/list.php?a=list")
    suspend fun getAreas(
        @Path("api_key")
        apiKey: String = Constants.API_KEY
    ): AreaResponse

    @GET("{api_key}/list.php?i=list")
    suspend fun getIngredients(
        @Path("api_key")
        apiKey: String = Constants.API_KEY
    ): IngredientResponse
}
