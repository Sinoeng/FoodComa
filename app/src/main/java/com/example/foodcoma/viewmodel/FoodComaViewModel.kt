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
import com.example.foodcoma.FoodComaScreen
import com.example.foodcoma.database.AreaRepository
import com.example.foodcoma.database.CategoryRepository
import com.example.foodcoma.database.IngredientRepository
import com.example.foodcoma.database.LocalRecipeRepository
import com.example.foodcoma.database.NetworkRecipeRepository
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
    data class Set(val recipeID: String) : SelectedRecipeIDUiState
    object Unset : SelectedRecipeIDUiState
}

class FoodComaViewModel(
    private val categoryRepository: CategoryRepository,
    private val areaRepository: AreaRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: NetworkRecipeRepository,
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

    var selectedRecipeIDUiState: SelectedRecipeIDUiState by mutableStateOf(SelectedRecipeIDUiState.Unset)

    init {
        getStarter()
    }

    fun getStarter() {
        getCategories()
        getAreas()
        getIngredients()
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryListUiState = CategoryListUiState.Loading
            categoryListUiState = try {
                CategoryListUiState.Success(categoryRepository.getCategories().categories)
                    .also {
                        categoryRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    categoryRepository.scheduleReload()
                    CategoryListUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    private fun getAreas() {
        viewModelScope.launch {
            areaListUiState = AreaListUiState.Loading
            areaListUiState = try {
                AreaListUiState.Success(areaRepository.getAreas().areas)
                    .also {
                        areaRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    areaRepository.scheduleReload()
                    AreaListUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    private fun getIngredients() {
        viewModelScope.launch {
            ingredientListUiState = IngredientListUiState.Loading
            ingredientListUiState = try {
                IngredientListUiState.Success(ingredientRepository.getIngredients().ingredients)
                    .also {
                        ingredientRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    ingredientRepository.scheduleReload()
                    IngredientListUiState.Error
                } else {
                    throw e
                }
            }
        }
    }


    fun setSelectedCategory(category: Category) {
        viewModelScope.launch {
            selectedCategoryUiState = SelectedCategoryUiState.Loading
            selectedCategoryUiState = try {
                SelectedCategoryUiState.Success(category)
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    SelectedCategoryUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    fun setSelectedArea(area: Area) {
        viewModelScope.launch {
            selectedAreaUiState = SelectedAreaUiState.Loading
            selectedAreaUiState = try {
                SelectedAreaUiState.Success(area)
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    SelectedAreaUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    fun setSelectedIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            selectedIngredientUiState = SelectedIngredientUiState.Loading
            selectedIngredientUiState = try {
                SelectedIngredientUiState.Success(ingredient)
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    SelectedIngredientUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    fun getRecipeListByCategory(category: String) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByCategory(category).meals!!)
                    .also {
                        recipeRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException || e is NullPointerException) {
                    try {
                        recipeRepository.scheduleReload(FoodComaScreen.CategoryDetail.name, inpData = category)
                        RecipeListUiState.Success(localRepository.getRecipeByCategory(category).meals!!)
                    } catch (e: Exception) {
                        if (e is IOException || e is HttpException || e is NullPointerException) {
                            RecipeListUiState.Error
                        } else {
                            throw e
                        }
                    }
                } else {
                    throw e
                }
            }
        }
    }

    fun getRecipeListByArea(area: String) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByArea(area).meals!!)
                    .also {
                        recipeRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException || e is NullPointerException) {
                    try {
                        recipeRepository.scheduleReload(FoodComaScreen.AreaDetail.name, inpData = area)
                        RecipeListUiState.Success(localRepository.getRecipeByArea(area).meals!!)
                    } catch (e: Exception) {
                        if (e is IOException || e is HttpException || e is NullPointerException) {
                            RecipeListUiState.Error
                        } else {
                            throw e
                        }
                    }
                } else {
                    throw e
                }
            }
        }
    }

    fun getRecipeListByIngredient(ingredient: String) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeByIngredient(ingredient).meals!!)
                    .also {
                        recipeRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException || e is NullPointerException) {
                    try {
                        recipeRepository.scheduleReload(FoodComaScreen.IngredientDetail.name, inpData = ingredient)
                        RecipeListUiState.Success(localRepository.getRecipeByIngredient(ingredient).meals!!)
                    } catch (e: Exception) {
                        if (e is IOException || e is HttpException || e is NullPointerException) {
                            RecipeListUiState.Error
                        } else {
                            throw e
                        }
                    }
                } else {
                    throw e
                }
            }
        }
    }

    fun getRecipeListBySearch(query: String) {
        viewModelScope.launch {
            recipeListUiState = RecipeListUiState.Loading
            recipeListUiState = try {
                RecipeListUiState.Success(recipeRepository.getRecipeBySearch(query).meals!!)
                    .also {
                        recipeRepository.cancelScheduledReload()
                    }
            } catch (e: Exception) {
                if (e is IOException || e is HttpException || e is NullPointerException) {
                    try {
                        RecipeListUiState.Success(localRepository.getRecipeBySearch(query).meals!!)
                    } catch (e: Exception) {
                        if (e is IOException || e is HttpException || e is NullPointerException) {
                            RecipeListUiState.Error
                        } else {
                            throw e
                        }
                    }
                } else {
                    throw e
                }
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
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    RecipeListUiState.Error
                } else {
                    throw e
                }
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            localRepository.clearDatabase()
        }
    }

    fun setSelectedRecipe(recipeID: String) {
        viewModelScope.launch {
            selectedRecipeUiState = SelectedRecipeUiState.Loading
            selectedRecipeIDUiState = SelectedRecipeIDUiState.Unset
            selectedRecipeUiState = try {
                selectedRecipeIDUiState = SelectedRecipeIDUiState.Set(recipeID)
                val recipe = recipeRepository.getRecipeByID(recipeID).meals[0]
                recipeRepository.cancelScheduledReload()
                SelectedRecipeUiState.Success(recipe, localRepository.getFavoriteRecipeByID(recipeID) != null)
            } catch (e: Exception) {
                if (e is IOException || e is HttpException) {
                    try {
                        val recipe = localRepository.getRecipeByID(recipeID)!!.meals[0]
                        SelectedRecipeUiState.Success(recipe, localRepository.getFavoriteRecipeByID(recipeID) != null)
                    } catch (e: Exception) {
                        if (e is IOException || e is HttpException || e is NullPointerException) {
                            SelectedRecipeUiState.Error
                        } else {
                            throw e
                        }
                    }
                } else {
                    throw e
                }
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


