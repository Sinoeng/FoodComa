package com.example.foodcoma.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName(value = "idCategory")
    var idCategory: String = "",

    @SerialName(value = "strCategory")
    var strCategory: String = "",

    @SerialName(value = "strCategoryThumb")
    var strCategoryThumb: String = "",

    @SerialName(value = "strCategoryDescription")
    var strCategoryDescription: String = ""
)
