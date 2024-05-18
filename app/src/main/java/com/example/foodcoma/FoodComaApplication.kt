package com.example.foodcoma

import android.app.Application
import com.example.foodcoma.database.AppContainer
import com.example.foodcoma.database.DefaultAppContainer

class FoodComaApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}