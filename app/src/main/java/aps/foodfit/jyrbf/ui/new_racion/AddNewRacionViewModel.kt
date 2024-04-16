package aps.foodfit.jyrbf.ui.new_racion

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase
import aps.foodfit.jyrbf.data.remote.RecipeItemShort
import aps.foodfit.jyrbf.domain.saveBitmap
import kotlinx.coroutines.launch

class AddNewRacionViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()
    private val listOfRecipeItems = mutableListOf<RecipeItemShort>()
    val recipeListLD = MutableLiveData<List<RecipeItemShort>>()
    val isRecipeSaved = MutableLiveData<Boolean>()
    private val context by lazy { application.applicationContext }
    fun addRecipeItem(recipe: RecipeItemShort, weightInGrams:Int) {
        val recipeItem = recipe.copy(weight = weightInGrams, label = recipe.label?.trim())
        recipeItem.imgBitmap = recipe.imgBitmap
        listOfRecipeItems.add(recipeItem)
        recipeListLD.value = listOfRecipeItems
    }

    fun saveRacion(racionName: String) {
        viewModelScope.launch {
            val listDB = mutableListOf<RecipeDB>()
            listOfRecipeItems.map {
                listDB.add(saveImgAndConvertToDB(it, racionName))
            }
            recipeDao.addListOfRecipeItems(listDB)
            isRecipeSaved.postValue(true)
        }
    }

    fun resetState(){
        listOfRecipeItems.clear()
        recipeListLD.value = listOfRecipeItems
        isRecipeSaved.postValue(false)
    }

    private fun saveImgAndConvertToDB(recipe:RecipeItemShort, racionName:String):RecipeDB{
        val savedImgName = recipe.label?:"savedImg"
        if (recipe.imgBitmap != null) {
            saveImg(savedImgName, recipe.imgBitmap!!)
        }
        recipe.savedImgName = "${savedImgName}.webp"
        return recipe.toRecipeDB(racionName)
    }

    private fun saveImg(imgName: String, imgBitmap: Bitmap) {
        viewModelScope.launch {
            context.saveBitmap(imgName, imgBitmap)
        }
    }

}