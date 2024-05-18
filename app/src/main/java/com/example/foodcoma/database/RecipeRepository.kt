package com.example.foodcoma.database

import com.example.foodcoma.model.FavoriteRecipeEntity
import com.example.foodcoma.model.RecipeResponse
import com.example.foodcoma.model.RecipeShort
import com.example.foodcoma.model.RecipeShortResponse
import com.example.foodcoma.network.FoodComaApiService


interface RecipeRepository {
    suspend fun getRecipeByID(id: String): RecipeResponse?

    suspend fun getRecipeBySearch(name: String): RecipeShortResponse?

    suspend fun getRecipeByIngredient(ingredient: String): RecipeShortResponse?

    suspend fun getRecipeByCategory(category: String): RecipeShortResponse?

    suspend fun getRecipeByArea(area: String): RecipeShortResponse?
}


class NetworkRecipeRepository(private val apiService: FoodComaApiService) : RecipeRepository {
    override suspend fun getRecipeByID(id: String): RecipeResponse {
        return apiService.getRecipeById(id = id)
    }

    override suspend fun getRecipeBySearch(name: String): RecipeShortResponse {
        return apiService.getRecipeBySearch(name = name)
    }

    override suspend fun getRecipeByIngredient(ingredient: String): RecipeShortResponse {
        return apiService.getRecipeByIngredient(ingredient = ingredient)
    }

    override suspend fun getRecipeByCategory(category: String): RecipeShortResponse {
        return apiService.getRecipeByCategory(category = category)
    }

    override suspend fun getRecipeByArea(area: String): RecipeShortResponse {
        return apiService.getRecipeByArea(area = area)
    }
}

class LocalRecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    suspend fun insertFavoriteRecipe(recipeID: String) {
        recipeDao.insertFavorite(FavoriteRecipeEntity(recipeId = recipeID))
    }

    suspend fun removeFavoriteRecipe(recipeID: String) {
        recipeDao.deleteFavorite(FavoriteRecipeEntity(recipeId = recipeID))
    }

    override suspend fun getRecipeByID(id: String): RecipeResponse? {
        val recipeList = recipeDao.getRecipeByID(id)
        if (recipeList.isEmpty()) {
            return null
        }
        return RecipeResponse(meals = recipeList)
    }

    override suspend fun getRecipeBySearch(name: String): RecipeShortResponse {
        return RecipeShortResponse(meals = recipeDao.searchRecipes(name).map { recipe ->
            RecipeShort(
                idMeal = recipe.idMeal,
                strMeal = recipe.strMeal,
                strMealThumb = recipe.strMealThumb
            )
        })
    }

    override suspend fun getRecipeByIngredient(ingredient: String): RecipeShortResponse {
        return RecipeShortResponse(meals = recipeDao.getRecipeByIngredient(ingredient))
    }

    override suspend fun getRecipeByCategory(category: String): RecipeShortResponse {
        return RecipeShortResponse(meals = recipeDao.getRecipeByCategory(category))
    }

    override suspend fun getRecipeByArea(area: String): RecipeShortResponse {
        return RecipeShortResponse(meals = recipeDao.getRecipeByArea(area))
    }
}