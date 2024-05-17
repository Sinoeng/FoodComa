package com.example.foodcoma.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodcoma.FoodComaApplication
import com.example.foodcoma.database.CategoryRepository
import com.example.foodcoma.database.NetworkCategoryRepository
import com.example.foodcoma.database.RecipeRepository
import com.example.foodcoma.model.Category
import com.example.foodcoma.model.Recipe
import com.example.foodcoma.model.RecipeShort
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface CategoryListUiState {
    data class Success(val categoryList: List<Category>) : CategoryListUiState
    object Loading : CategoryListUiState
    object Error : CategoryListUiState
}

sealed interface SelectedCategoryUiState {
    data class Success(val category: Category) : SelectedCategoryUiState
    object Loading : SelectedCategoryUiState
    object Error : SelectedCategoryUiState
}

sealed interface RecipeListUiState {
    data class Success(val recipeList: List<RecipeShort>) : RecipeListUiState
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
}

sealed interface SelectedRecipeUiState {
    data class Success(val recipeID: String) : SelectedRecipeUiState
    object Loading : SelectedRecipeUiState
    object Error : SelectedRecipeUiState
}

class FoodComaViewModel(
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository
): ViewModel() {
    var categoryListUiState: CategoryListUiState by mutableStateOf(CategoryListUiState.Loading)
        private set

    var selectedCategoryUiState: SelectedCategoryUiState by mutableStateOf(SelectedCategoryUiState.Loading)
        private set

    var recipeListUiState: RecipeListUiState by mutableStateOf(RecipeListUiState.Loading)
        private set

    var selectedRecipeUiState: SelectedRecipeUiState by mutableStateOf(SelectedRecipeUiState.Loading)
        private set

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            categoryListUiState = CategoryListUiState.Loading
            categoryListUiState = try {
                CategoryListUiState.Success(categoryRepository.getCategories().categories)
            } catch (e: IOException) {
                CategoryListUiState.Error
            } catch (e: HttpException) {
                CategoryListUiState.Error
            }
        }
    }

    fun setSelectedCategory(category: Category) {
        viewModelScope.launch {
            selectedCategoryUiState = SelectedCategoryUiState.Loading
            selectedCategoryUiState = try {
                SelectedCategoryUiState.Success(category)
            } catch (e: IOException) {
                SelectedCategoryUiState.Error
            } catch (e: HttpException) {
                SelectedCategoryUiState.Error
            }
        }
    }

    fun getRecipeListByCategory(category: Category) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByCategory(category.strCategory).meals)
            }catch (e: IOException) {
                RecipeListUiState.Error
            }catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }

    fun setSelectedRecipe(recipeID: String) {
        viewModelScope.launch {
            selectedRecipeUiState = SelectedRecipeUiState.Loading
            selectedRecipeUiState = try {
                SelectedRecipeUiState.Success(recipeID)
            } catch (e: IOException) {
                SelectedRecipeUiState.Error
            } catch (e: HttpException) {
            SelectedRecipeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FoodComaApplication)
                Log.d("FoodComaViewModelFactory", "FoodComaViewModel created!")
                val categoryRepository = application.container.categoryRepository
                Log.d("FoodComaViewModelFactory", "categoryRepository created!")
                val recipeRepository = application.container.recipeRepository
                Log.d("FoodComaViewModelFactory", "recipeRepository created!")
                FoodComaViewModel(
                    categoryRepository = categoryRepository,
                    recipeRepository = recipeRepository
                )
            }
        }
    }
}


