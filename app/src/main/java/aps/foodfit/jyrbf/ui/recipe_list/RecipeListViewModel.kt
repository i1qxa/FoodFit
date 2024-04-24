package aps.foodfit.jyrbf.ui.recipe_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()
    private val recipeSearch = MutableLiveData<String>("")
    val recipeList = recipeSearch.switchMap { recipeName ->
        if (recipeName.isEmpty()) recipeDao.getAllRecipes()
        else recipeDao.getRecipeListByName(recipeName)
    }

    fun setupTextForSearch(query:String){
        recipeSearch.value = "%$query%"
    }

}