package com.example.foodcoma.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.model.IngredientResponse
import com.example.foodcoma.network.FoodComaApiService
import com.example.foodcoma.utils.Constants
import com.example.foodcoma.utils.Constants.SCHEDULED_RELOAD_TAG
import com.example.foodcoma.workers.ScheduledRefreshWorker

interface IngredientRepository {
    suspend fun getIngredients(): IngredientResponse

    suspend fun scheduleReload()

    suspend fun cancelScheduledReload()
}

class NetworkIngredientRepository(
    private val apiService: FoodComaApiService,
    context: Context
) : IngredientRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun getIngredients(): IngredientResponse {
        return apiService.getIngredients()
    }

    override suspend fun scheduleReload() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val data = Data.Builder()
            .putString(Constants.RELOAD_PAGE_TAG, FoodComaScreen.Ingredient.name)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<ScheduledRefreshWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(SCHEDULED_RELOAD_TAG, androidx.work.ExistingWorkPolicy.REPLACE, workRequest)
    }

    override suspend fun cancelScheduledReload() {
        workManager.cancelUniqueWork(SCHEDULED_RELOAD_TAG)
    }
}