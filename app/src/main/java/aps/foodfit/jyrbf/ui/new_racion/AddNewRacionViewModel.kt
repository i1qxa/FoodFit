package aps.foodfit.jyrbf.ui.new_racion

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.domain.saveBitmap
import kotlinx.coroutines.launch

class AddNewRacionViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()
    val listOfRecipeItems = mutableListOf<RecipeItemShort>()
    val recipeListLD = MutableLiveData<List<RecipeItemShort>>()
    val isRecipeSaved = MutableLiveData<Any>()
    private val context by lazy { application.applicationContext }
    fun addRecipeItem(recipe: RecipeItemShort, weightInGrams:Int) {
        val imgLocal = "${recipe.label?.trim()}.webp"
        val recipeItem = recipe.copy(weight = weightInGrams, label = recipe.label?.trim(), )
        listOfRecipeItems.add(recipeItem)
        recipeListLD.value = listOfRecipeItems
    }

    fun saveRacion(racionName: String) {
        viewModelScope.launch {
            listOfRecipeItems.forEach {
                TODO("Не сохраняется имя картинки")
                val savedImgName = it.label?:"saved_img"
                if (it.imgBitmap != null) {
                    saveImg(savedImgName, it.imgBitmap!!)
                    it.savedImgName = savedImgName
                }
            }
            val listDB = listOfRecipeItems.map {
                it.toRecipeDB(100, racionName)
            }
            recipeDao.addListOfRecipeItems(listDB)
            isRecipeSaved.postValue(Unit)
        }
    }

    private fun saveImg(imgName: String, imgBitmap: Bitmap) {
        viewModelScope.launch {
            context.saveBitmap(imgName, imgBitmap)
        }
    }

}