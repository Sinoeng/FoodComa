package com.example.foodcoma.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.model.RecipeResponse
import com.example.foodcoma.model.RecipeShort
import com.example.foodcoma.model.RecipeShortResponse
import com.example.foodcoma.network.FoodComaApiService
import com.example.foodcoma.utils.Constants.RELOAD_PAGE_TAG
import com.example.foodcoma.utils.Constants.SCHEDULED_RELOAD_TAG
import com.example.foodcoma.workers.ScheduledRefreshWorker


interface RecipeRepository {
    suspend fun getRecipeByID(id: String): RecipeResponse?

    suspend fun getRecipeBySearch(name: String): RecipeShortResponse?

    suspend fun getRecipeByIngredient(ingredient: String): RecipeShortResponse?

    suspend fun getRecipeByCategory(category: String): RecipeShortResponse?

    suspend fun getRecipeByArea(area: String): RecipeShortResponse?
}


class NetworkRecipeRepository(
    private val apiService: FoodComaApiService,
    context: Context
) : RecipeRepository {

    private val workManager = WorkManager.getInstance(context)


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


    fun scheduleReload(tag: String, inpData: String? = null) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val data = Data.Builder()
            .putString(RELOAD_PAGE_TAG, tag)
            .putString(tag, inpData)


        val workRequest = OneTimeWorkRequestBuilder<ScheduledRefreshWorker>()
            .setInputData(data.build())
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(SCHEDULED_RELOAD_TAG, androidx.work.ExistingWorkPolicy.REPLACE, workRequest)
    }

    fun cancelScheduledReload() {
        workManager.cancelUniqueWork(SCHEDULED_RELOAD_TAG)
    }
}

class LocalRecipeRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    suspend fun insertFavoriteRecipe(recipe: Recipe) {
        recipeDao.insertFavorite(recipe)
    }

    suspend fun removeFavoriteRecipe(recipeID: String) {
        recipeDao.removeFavorite(recipeID)
    }

    suspend fun getFavoriteRecipes(): List<RecipeShort> {
        return recipeDao.getAllFavoriteRecipes()
    }

    suspend fun getFavoriteRecipeByID(recipeID: String): Recipe? {
        return recipeDao.getFavoriteRecipeByID(recipeID)
    }

    suspend fun clearDatabase() {
        recipeDao.clearDatabase()
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