package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientResponse(
    @SerialName(value = "meals")
    var ingredients: List<Ingredient> = listOf()
)
