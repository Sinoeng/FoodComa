package com.example.foodcoma.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodcoma.FoodComaApplication
import com.example.foodcoma.database.AreaRepository
import com.example.foodcoma.database.CategoryRepository
import com.example.foodcoma.database.IngredientRepository
import com.example.foodcoma.database.LocalRecipeRepository
import com.example.foodcoma.database.RecipeRepository
import com.example.foodcoma.model.Area
import com.example.foodcoma.model.Category
import com.example.foodcoma.model.Ingredient
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

sealed interface AreaListUiState {
    data class Success(val areas: List<Area>) : AreaListUiState
    object Loading : AreaListUiState
    object Error : AreaListUiState
}

sealed interface SelectedAreaUiState {
    data class Success(val area: Area) : SelectedAreaUiState
    object Loading : SelectedAreaUiState
    object Error : SelectedAreaUiState
}

sealed interface IngredientListUiState {
    data class Success(val ingredients: List<Ingredient>) : IngredientListUiState
    object Loading : IngredientListUiState
    object Error : IngredientListUiState
}

sealed interface SelectedIngredientUiState {
    data class Success(val ingredient: Ingredient) : SelectedIngredientUiState
    object Loading : SelectedIngredientUiState
    object Error : SelectedIngredientUiState
}

sealed interface RecipeListUiState {
    data class Success(val recipeList: List<RecipeShort>) : RecipeListUiState
    object Loading : RecipeListUiState
    object Error : RecipeListUiState
}

sealed interface SelectedRecipeUiState {
    data class Success(val recipe: Recipe, val isFavorite: Boolean) : SelectedRecipeUiState
    object Loading : SelectedRecipeUiState
    object Error : SelectedRecipeUiState
}

sealed interface SelectedRecipeIDUiState {
    data class Success(val recipeID: String) : SelectedRecipeIDUiState
    object Loading : SelectedRecipeIDUiState
    object Error : SelectedRecipeIDUiState
}


class FoodComaViewModel(
    private val categoryRepository: CategoryRepository,
    private val areaRepository: AreaRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val localRepository: LocalRecipeRepository
): ViewModel() {
    var categoryListUiState: CategoryListUiState by mutableStateOf(CategoryListUiState.Loading)
        private set

    var selectedCategoryUiState: SelectedCategoryUiState by mutableStateOf(SelectedCategoryUiState.Loading)
        private set

    var recipeListUiState: RecipeListUiState by mutableStateOf(RecipeListUiState.Loading)
        private set

    var selectedRecipeUiState: SelectedRecipeUiState by mutableStateOf(SelectedRecipeUiState.Loading)
        private set

    var areaListUiState: AreaListUiState by mutableStateOf(AreaListUiState.Loading)
        private set

    var selectedAreaUiState: SelectedAreaUiState by mutableStateOf(SelectedAreaUiState.Loading)
        private set

    var ingredientListUiState: IngredientListUiState by mutableStateOf(IngredientListUiState.Loading)
        private set

    var selectedIngredientUiState: SelectedIngredientUiState by mutableStateOf(SelectedIngredientUiState.Loading)
        private set

    init {
        getCategories()
        getAreas()
        getIngredients()
    }

    private fun getCategories() {
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

    private fun getAreas() {
        viewModelScope.launch {
            areaListUiState = AreaListUiState.Loading
            areaListUiState = try {
                AreaListUiState.Success(areaRepository.getAreas().areas)
            } catch (e: IOException) {
                AreaListUiState.Error
            } catch (e: HttpException) {
                AreaListUiState.Error
            }
        }
    }

    private fun getIngredients() {
        viewModelScope.launch {
            ingredientListUiState = IngredientListUiState.Loading
            ingredientListUiState = try {
                IngredientListUiState.Success(ingredientRepository.getIngredients().ingredients)
            } catch (e: IOException) {
                IngredientListUiState.Error
            } catch (e: HttpException) {
                IngredientListUiState.Error
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

    fun setSelectedArea(area: Area) {
        viewModelScope.launch {
            selectedAreaUiState = SelectedAreaUiState.Loading
            selectedAreaUiState = try {
                SelectedAreaUiState.Success(area)
            } catch (e: IOException) {
                SelectedAreaUiState.Error
            } catch (e: HttpException) {
                SelectedAreaUiState.Error
            }
        }
    }

    fun setSelectedIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            selectedIngredientUiState = SelectedIngredientUiState.Loading
            selectedIngredientUiState = try {
                SelectedIngredientUiState.Success(ingredient)
            } catch (e: IOException) {
                SelectedIngredientUiState.Error
            } catch (e: HttpException) {
                SelectedIngredientUiState.Error
            }
        }
    }

    fun getRecipeListByCategory(category: Category) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByCategory(category.strCategory)!!.meals)
            }catch (e: IOException) {
                RecipeListUiState.Error
            }catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }

    fun getRecipeListByArea(area: Area) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByArea(area.strArea)!!.meals)
            } catch (e: IOException) {
                RecipeListUiState.Error
            } catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }

    fun getRecipeListByIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByIngredient(ingredient.strIngredient)!!.meals)
            } catch (e: IOException) {
                RecipeListUiState.Error
            } catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }


    fun setFavoriteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            val state = selectedRecipeUiState
            if (state is SelectedRecipeUiState.Success) {
                localRepository.insertFavoriteRecipe(recipe)
                selectedRecipeUiState = SelectedRecipeUiState.Success(state.recipe, true)
            }
        }
    }

    fun unsetFavoriteRecipe(recipeID: String) {
        viewModelScope.launch {
            val state = selectedRecipeUiState
            if (state is SelectedRecipeUiState.Success) {
                localRepository.removeFavoriteRecipe(recipeID)
                selectedRecipeUiState = SelectedRecipeUiState.Success(state.recipe, false)
            }
        }
    }

    fun getFavoriteRecipes() {
        viewModelScope.launch {
            selectedRecipeUiState = SelectedRecipeUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(localRepository.getFavoriteRecipes())
            } catch (e: IOException) {
                RecipeListUiState.Error
            } catch (e: HttpException) {
                RecipeListUiState.Error
            }
        }
    }

    fun setSelectedRecipe(recipeID: String) {
        viewModelScope.launch {
            selectedRecipeUiState = SelectedRecipeUiState.Loading
            selectedRecipeUiState = try {
                val recipe = recipeRepository.getRecipeByID(recipeID)!!.meals[0]      // TODO: perhaps an assert to make sure it isn't longer than 1
                SelectedRecipeUiState.Success(recipe, localRepository.getFavoriteRecipeByID(recipeID) != null)
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
                val categoryRepository = application.container.categoryRepository
                val areaRepository = application.container.areaRepository
                val ingredientRepository = application.container.ingredientRepository
                val recipeRepository = application.container.recipeRepository
                val localRepository = application.container.localRecipeRepository
                FoodComaViewModel(
                    categoryRepository = categoryRepository,
                    areaRepository = areaRepository,
                    ingredientRepository = ingredientRepository,
                    recipeRepository = recipeRepository,
                    localRepository = localRepository
                )
            }
        }
    }
}


