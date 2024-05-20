package com.example.foodcoma.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.model.CategoryResponse
import com.example.foodcoma.network.FoodComaApiService
import com.example.foodcoma.utils.Constants.RELOAD_PAGE_TAG
import com.example.foodcoma.utils.Constants.SCHEDULED_RELOAD_TAG
import com.example.foodcoma.workers.ScheduledRefreshWorker

interface CategoryRepository {
    suspend fun getCategories(): CategoryResponse

    suspend fun scheduleReload()

    suspend fun cancelScheduledReload()
}

class NetworkCategoryRepository(
    private val apiService: FoodComaApiService,
    context: Context
) : CategoryRepository {
    private val workManager = WorkManager.getInstance(context)      // TODO: check if this works as a singleton

    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategories()
    }

    override suspend fun scheduleReload() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val data = Data.Builder()
            .putString(RELOAD_PAGE_TAG, FoodComaScreen.Categories.name)
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