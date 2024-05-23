package com.example.foodcoma.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.foodcoma.model.FavoriteRecipeEntity
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.model.RecipeShort

private const val strIngredientList = "strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20"
@Dao
interface RecipeDao {
    @Query("SELECT recipe_id FROM favorite_recipes")
    suspend fun getAllFavoriteIDs(): List<String>

    //@Query("SELECT idMeal, strMeal, strMealThumb FROM recipes LEFT JOIN favorite_recipes ON recipes.idMeal = favorite_recipes.recipe_id") // broken for some reason
    @Query("SELECT idMeal, strMeal, strMealThumb FROM recipes WHERE idMeal IN (SELECT recipe_id FROM favorite_recipes)")
    suspend fun getAllFavoriteRecipes(): List<RecipeShort>

    @Query("SELECT * FROM recipes LEFT JOIN favorite_recipes ON recipes.idMeal = favorite_recipes.recipe_id WHERE favorite_recipes.recipe_id = :recipeID")
    suspend fun getFavoriteRecipeByID(recipeID: String): Recipe?

    @Query("SELECT * FROM recipes WHERE idMeal = :recipeID")
    suspend fun getRecipeByID(recipeID: String): List<Recipe>

    @Query("SELECT * FROM recipes WHERE strMeal LIKE '%' || :query || '%'")
    suspend fun searchRecipes(query: String): List<Recipe>

    @Query("SELECT idMeal, strMeal, strMealThumb FROM recipes WHERE :ingredient IN ($strIngredientList)")
    suspend fun getRecipeByIngredient(ingredient: String): List<RecipeShort>

    @Query("SELECT idMeal, strMeal, strMealThumb FROM recipes WHERE :category = strCategory")
    suspend fun getRecipeByCategory(category: String): List<RecipeShort>

    @Query("SELECT idMeal, strMeal, strMealThumb FROM recipes WHERE :area = strArea")
    suspend fun getRecipeByArea(area: String): List<RecipeShort>

    @Transaction
    suspend fun insertFavorite(recipe: Recipe) {
        insertRecipe(recipe)
        insertFavoriteRecipe(FavoriteRecipeEntity(recipeId = recipe.idMeal))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favRecipe: FavoriteRecipeEntity)

    @Query("DELETE FROM favorite_recipes WHERE recipe_id = :recipeID")
    suspend fun removeFavorite(recipeID: String)

    @Query("DELETE FROM recipes WHERE recipes.idMeal NOT IN (SELECT recipe_id FROM favorite_recipes)")
    suspend fun clearDatabase()

}