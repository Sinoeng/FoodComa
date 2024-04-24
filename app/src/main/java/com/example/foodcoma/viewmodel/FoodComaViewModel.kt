package com.example.foodcoma.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodcoma.model.Recipe
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RecipeListUiState {
    data class Success(val recipeList: List<Recipe>) : RecipeListUiState
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
}

sealed interface SelectedRecipeUiState {
    data class Success(val recipe: Recipe) : SelectedRecipeUiState
    object Loading : SelectedRecipeUiState
    object Error : SelectedRecipeUiState
}

class FoodComaViewModel(

): ViewModel() {
    var recipeListUiState: RecipeListUiState by mutableStateOf(RecipeListUiState.Loading)
        private set

    var selectedRecipeUiState: SelectedRecipeUiState by mutableStateOf(SelectedRecipeUiState.Loading)
        private set


    fun getRecipeList(recipeList: List<Recipe>) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeListUiState.getPopularMovies().results) // TODO: decide how to database things after unit 6
            }catch (e: IOException) {
                RecipeListUiState.Error
            }catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }

    fun setSelectedRecipe(recipe: Recipe) {
        viewModelScope.launch {
            selectedRecipeUiState = SelectedRecipeUiState.Loading
            selectedRecipeUiState = try {
                SelectedRecipeUiState.Success(recipe)
            } catch (e: IOException) {
                SelectedRecipeUiState.Error
            } catch (e: HttpException) {
            SelectedRecipeUiState.Error
            }
        }
    }
}


