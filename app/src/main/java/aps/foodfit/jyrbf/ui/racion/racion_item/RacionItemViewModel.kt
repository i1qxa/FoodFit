package aps.foodfit.jyrbf.ui.racion.racion_item

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase

class RacionItemViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()

    private val racionNameLD = MutableLiveData<String>()
    val recipeList = racionNameLD.switchMap { racionName ->
        recipeDao.getRacion(racionName)
    }

    fun setupRacionName(racionName:String){
        racionNameLD.value = racionName
    }

}