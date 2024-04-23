package aps.foodfit.jyrbf.ui.racion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase
import aps.foodfit.jyrbf.domain.Racion
import aps.foodfit.jyrbf.domain.RacionAndImg

class RacionViewModel(application: Application) : AndroidViewModel(application) {

    private val context by lazy { application.baseContext }

    private val recipeDao = RecipeDataBase.getInstance(application).recipeDao()

    val listOfRacion = recipeDao.getListOfRacion()

//    val listOfRacionAndImg = listOfRacion.switchMap { racionList ->
//        MutableLiveData(racionList.map {
//            convertRacionToRacionAndDrawable(it)
//        })
//    }
//
//    private fun convertRacionToRacionAndDrawable(racion:Racion):RacionAndImg{
//        return RacionAndImg(
//            racion.name,
//            racion.imgRemote,
//            racion.imgLocal,
//            racion.recipeAmount,
//            racion.totalTime,
//            racion.totalWeight,
//            racion.totalKcal,
//            racion.totalProtein,
//            racion.totalFat,
//            racion.totalCarb,
//            racion.isPrePopulate,
//            racion.getSavedImg(context)
//        )
//    }


}