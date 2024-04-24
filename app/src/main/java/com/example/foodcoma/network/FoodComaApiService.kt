package com.example.foodcoma.network


import com.example.foodcoma.model.CategoryResponse
import com.example.foodcoma.model.RecipeResponse
import com.example.foodcoma.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodComaApiService {
    @GET("categories")      // .php ?
    suspend fun getCategories(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): CategoryResponse

    @GET("lookup.php?i={id}")
    suspend fun getRecipeById(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Path("id")
        id: String
    ): RecipeResponse

    @GET("search.php?s={id}")
    suspend fun getRecipeBySearch(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Path("id")
        id: String
    )

    @GET("filter.php?i={ingredient}")
    suspend fun getRecipeByIngredient(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Path("ingredient")
        ingredient: String
    )

    @GET("filter.php?c={category}")
    suspend fun getRecipeByCategory(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Path("category")
        category: String
    )

    @GET("filter.php?c={area}")
    suspend fun getRecipeByArea(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Path("area")
        area: String
    )

    // TODO: list by first letter?

    @GET("list.php?a=list")
    suspend fun getAreas(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    )

    @GET("list.php?i=list")
    suspend fun getIngredients(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    )


}
