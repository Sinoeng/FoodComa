package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName(value = "meals")
    var meals: List<Receipe> = listOf()
)
