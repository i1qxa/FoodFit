package aps.foodfit.jyrbf.ui.racion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase

class RacionViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()

    val listOfRacion = recipeDao.getListOfRacion()



}