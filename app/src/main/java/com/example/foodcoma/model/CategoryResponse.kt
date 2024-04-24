package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName(value = "categories")
    var categories: List<Category> = listOf()
)
