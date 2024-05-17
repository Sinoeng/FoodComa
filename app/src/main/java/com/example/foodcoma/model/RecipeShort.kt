package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeShort(
    @SerialName(value = "idMeal")
    var idMeal: String = "",

    @SerialName(value = "strMeal")
    var strMeal: String = "",

    @SerialName(value = "strMealThumb")
    var strMealThumb: String = ""
)