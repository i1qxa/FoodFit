package aps.foodfit.jyrbf.domain

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.net.toUri

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

//    val savedImg = Drawable.createFromPath("file:///android_asset/${imgLocal}")

    fun getDrawableFromAsset():Drawable?{
//        val assetManager = context.assets
//        val inputStream = assetManager.open(imgLocal)
//        Drawable.createFromPath()
        return Drawable.createFromPath("file:///android_asset/${imgLocal}")
    }

}
