package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaResponse(
    @SerialName(value = "meals")
    var areas : List<Area> = listOf()
)
