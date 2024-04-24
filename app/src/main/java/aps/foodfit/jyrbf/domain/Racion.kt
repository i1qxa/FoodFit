package aps.foodfit.jyrbf.domain

import android.content.Context
import android.graphics.Bitmap

data class Racion(
    val name:String,
    val imgRemote:String,
    val imgLocal:String,
    val recipeAmount:Int,
    val totalTime:Int,
    val totalWeight:Int,
    val totalKcal:Int,
    val totalProtein:Int,
    val totalFat:Int,
    val totalCarb:Int,
    val isPrePopulate:Boolean,
){
    fun getSavedImg(context: Context): Bitmap? {
        return when (isPrePopulate) {
            false -> {
                context.getBitmapByName(imgLocal)
            }
            true -> {
                context.getBitmapByNameFromAssets(imgLocal)
            }
        }
    }

}
