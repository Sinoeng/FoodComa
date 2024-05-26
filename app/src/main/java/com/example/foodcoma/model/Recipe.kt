package com.example.foodcoma.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "recipes", indices = [Index(value = ["idMeal"], unique = true)])
data class Recipe(
    @SerialName(value = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @SerialName(value = "idMeal")
    var idMeal: String = "",

    @SerialName(value = "strMeal")
    var strMeal: String = "",

    @SerialName(value = "strCategory")
    var strCategory: String = "",

    @SerialName(value = "strArea")
    var strArea: String = "",

    @SerialName(value = "strInstructions")
    var strInstructions: String = "",

    @SerialName(value = "strMealThumb")
    var strMealThumb: String = "",

    @SerialName(value = "strYoutube")
    var strYoutube: String? = null,

    @SerialName(value = "strSource")
    var strSource: String? = null,

    @SerialName(value = "strIngredient1")
    var strIngredient1: String? = null,
    @SerialName(value = "strIngredient2")
    var strIngredient2: String? = null,
    @SerialName(value = "strIngredient3")
    var strIngredient3: String? = null,
    @SerialName(value = "strIngredient4")
    var strIngredient4: String? = null,
    @SerialName(value = "strIngredient5")
    var strIngredient5: String? = null,
    @SerialName(value = "strIngredient6")
    var strIngredient6: String? = null,
    @SerialName(value = "strIngredient7")
    var strIngredient7: String? = null,
    @SerialName(value = "strIngredient8")
    var strIngredient8: String? = null,
    @SerialName(value = "strIngredient9")
    var strIngredient9: String? = null,
    @SerialName(value = "strIngredient10")
    var strIngredient10: String? = null,
    @SerialName(value = "strIngredient11")
    var strIngredient11: String? = null,
    @SerialName(value = "strIngredient12")
    var strIngredient12: String? = null,
    @SerialName(value = "strIngredient13")
    var strIngredient13: String? = null,
    @SerialName(value = "strIngredient14")
    var strIngredient14: String? = null,
    @SerialName(value = "strIngredient15")
    var strIngredient15: String? = null,
    @SerialName(value = "strIngredient16")
    var strIngredient16: String? = null,
    @SerialName(value = "strIngredient17")
    var strIngredient17: String? = null,
    @SerialName(value = "strIngredient18")
    var strIngredient18: String? = null,
    @SerialName(value = "strIngredient19")
    var strIngredient19: String? = null,
    @SerialName(value = "strIngredient20")
    var strIngredient20: String? = null,

    @SerialName(value = "strMeasure1")
    var strMeasure1: String? = null,
    @SerialName(value = "strMeasure2")
    var strMeasure2: String? = null,
    @SerialName(value = "strMeasure3")
    var strMeasure3: String? = null,
    @SerialName(value = "strMeasure4")
    var strMeasure4: String? = null,
    @SerialName(value = "strMeasure5")
    var strMeasure5: String? = null,
    @SerialName(value = "strMeasure6")
    var strMeasure6: String? = null,
    @SerialName(value = "strMeasure7")
    var strMeasure7: String? = null,
    @SerialName(value = "strMeasure8")
    var strMeasure8: String? = null,
    @SerialName(value = "strMeasure9")
    var strMeasure9: String? = null,
    @SerialName(value = "strMeasure10")
    var strMeasure10: String? = null,
    @SerialName(value = "strMeasure11")
    var strMeasure11: String? = null,
    @SerialName(value = "strMeasure12")
    var strMeasure12: String? = null,
    @SerialName(value = "strMeasure13")
    var strMeasure13: String? = null,
    @SerialName(value = "strMeasure14")
    var strMeasure14: String? = null,
    @SerialName(value = "strMeasure15")
    var strMeasure15: String? = null,
    @SerialName(value = "strMeasure16")
    var strMeasure16: String? = null,
    @SerialName(value = "strMeasure17")
    var strMeasure17: String? = null,
    @SerialName(value = "strMeasure18")
    var strMeasure18: String? = null,
    @SerialName(value = "strMeasure19")
    var strMeasure19: String? = null,
    @SerialName(value = "strMeasure20")
    var strMeasure20: String? = null

)
