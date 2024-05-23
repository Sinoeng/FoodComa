package com.example.foodcoma.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.utils.Constants.RELOAD_PAGE_TAG
import com.example.foodcoma.viewmodel.FoodComaViewModel

class ScheduledRefreshWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {

        val page = inputData.getString(RELOAD_PAGE_TAG)

        try {
            when (page) {
                FoodComaScreen.Category.name -> {
                    viewmodel!!.getStarter()
                }
                FoodComaScreen.Area.name -> {
                    viewmodel!!.getStarter()
                }
                FoodComaScreen.Ingredient.name -> {
                    viewmodel!!.getStarter()
                }
                FoodComaScreen.CategoryDetail.name -> {
                    val category = inputData.getString(page)
                    viewmodel!!.getRecipeListByCategory(category!!)
                }
                FoodComaScreen.AreaDetail.name -> {
                    val area = inputData.getString(page)
                    viewmodel!!.getRecipeListByArea(area!!)
                }
                FoodComaScreen.IngredientDetail.name -> {
                    val ingredient = inputData.getString(page)
                    viewmodel!!.getRecipeListByIngredient(ingredient!!)
                }
                FoodComaScreen.RecipeDetail.name -> {
                    val recipeID = inputData.getString(page)
                    viewmodel!!.setSelectedRecipe(recipeID!!)
                }
                else -> {
                    return Result.failure()
                }
            }
        }
        catch (e: NullPointerException) {
            return Result.failure()
        }

        return Result.success()
    }


    companion object {
        var viewmodel: FoodComaViewModel? = null
    }

}