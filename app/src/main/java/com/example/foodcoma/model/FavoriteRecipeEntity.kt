package com.example.foodcoma.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes", foreignKeys = [ForeignKey(entity = Recipe::class, parentColumns = ["idMeal"], childColumns = ["recipe_id"])])
data class FavoriteRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "recipe_id")
    var recipeId: String
)
