package com.example.foodcoma.database

import android.content.Context
import com.example.foodcoma.network.FoodComaApiService
import com.example.foodcoma.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val categoryRepository: CategoryRepository
    val areaRepository: AreaRepository
    val ingredientRepository: IngredientRepository
    val recipeRepository: RecipeRepository
    val localRecipeRepository: LocalRecipeRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {

    private fun getLoggerInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    private val foodComaJson = Json {
        ignoreUnknownKeys = true
    }


    private val retrofit = Retrofit.Builder()
        .client(
            okhttp3.OkHttpClient.Builder()
                .addInterceptor(getLoggerInterceptor())
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(foodComaJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.SERVER_BASE_URL)
        .build()

    private val retrofitService: FoodComaApiService by lazy {
        retrofit.create(FoodComaApiService::class.java)
    }

    override val categoryRepository: CategoryRepository by lazy {
        NetworkCategoryRepository(retrofitService)
    }

    override val areaRepository: AreaRepository by lazy {
        NetworkAreaRepository(retrofitService)
    }

    override val ingredientRepository: IngredientRepository by lazy {
        NetworkIngredientRepository(retrofitService)
    }

    override val recipeRepository: RecipeRepository by lazy {
        NetworkRecipeRepository(retrofitService)
    }

    override val localRecipeRepository: LocalRecipeRepository by lazy {
        LocalRecipeRepository(RecipeDatabase.getDatabase(context).recipeDao())
    }

}