package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("strIngredient")
    var strIngredient: String = "",

    @SerialName("strDescription")
    var strDescription: String? = null,
)
