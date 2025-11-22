package io.luisferlcc.ulsb.recipeapp.domain.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.luisferlcc.ulsb.recipeapp.data.KtorfitClient
import io.luisferlcc.ulsb.recipeapp.domain.dtos.Prompt
import io.luisferlcc.ulsb.recipeapp.domain.dtos.RecipeDTO
import io.luisferlcc.ulsb.recipeapp.domain.utils.Preferences
import kotlinx.coroutines.launch


class RecipeViewModel: ViewModel() {
    val userId = Preferences.getUserId() ?: 0
    val recipeService = KtorfitClient.createRecipeService()

    var recipes by mutableStateOf<List<Recipe>>(listOf())
    var generatedRecipe by mutableStateOf<RecipeDTO?>(null)
    var generatedRecipeIsSaved by mutableStateOf(true)
    var showSheet by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    init {
        getRecipes()
    }

    fun showModalFromList(recipe: RecipeDTO) {
        generatedRecipe = recipe
        showSheet = true
    }

    fun hideModal() {
        showSheet = false
    }

    fun generateRecipe(prompt: Prompt) {
        viewModelScope.launch {
            try {
                isLoading = true
                val result = recipeService.generateRecipe(prompt)
                showSheet = true
                generatedRecipe = result
                generatedRecipeIsSaved = false
                println(result.toString())
            } catch (e: Exception) {
                showSheet = false
                println(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                val result = recipeService.getRecipesByUserId(userId)
                recipes = result.reversed()
                println(result.toString())
            } catch (e: Exception) {
                println(e.toString())
            }
        }
    }

    fun saveRecipeInDB() {
        viewModelScope.launch {
            try {
                val recipe = Recipe(
                    id = 0,
                    userId = userId,
                    category = generatedRecipe?.category ?: "",
                    imageUrl = generatedRecipe?.imageUrl,
                    ingredients = generatedRecipe?.ingredients ?: emptyList(),
                    instructions = generatedRecipe?.instructions ?: emptyList(),
                    minutes = generatedRecipe?.minutes ?: 0,
                    stars = generatedRecipe?.stars ?: 0,
                    title = generatedRecipe?.title ?: ""
                )

                val result = recipeService.saveGeneratedRecipe(recipe)
                println(result.toString())
                getRecipes()
            } catch (e: Exception) {
                println(e.toString())
            }
            finally {
                generatedRecipeIsSaved = true
            }
        }
    }
}