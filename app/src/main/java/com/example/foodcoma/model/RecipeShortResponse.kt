package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeShortResponse(
    @SerialName(value = "meals")
    var meals: List<RecipeShort> = listOf()
)

