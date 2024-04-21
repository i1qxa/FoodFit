package aps.foodfit.jyrbf.data.local.recipe

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import aps.foodfit.jyrbf.domain.getBitmapByName

@Entity
data class RecipeDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imgRemote: String,
    val imgLocal: String,
    val weightInGrams: Int,
    val kcalPerGram: Double,
    val proteinPerGram: Double,
    val fatPerGram: Double,
    val carbPerGram: Double,
    val mealType: Int ,
    val ingredients: String,
    val totalTimeInMinutes: Int,
    val racionName: String,
    val isPrePopulate: Boolean,
    val uri:String,
) {
    val kcalTotal: Double
        get() = kcalPerGram * weightInGrams
    val proteinTotal: Double
        get() = proteinPerGram * weightInGrams
    val fatTotal: Double
        get() = fatPerGram * weightInGrams
    val carbTotal: Double
        get() = carbPerGram * weightInGrams

    val proteinPercent:Int
        get() = ((proteinTotal/totalNuntrientsMass)*100).toInt()

    val fatPercent:Int
        get() = ((fatTotal/totalNuntrientsMass)*100).toInt()

    val carbPercent:Int
        get() = ((carbTotal/totalNuntrientsMass)*100).toInt()
    private val totalNuntrientsMass:Int
        get() = (proteinTotal + fatTotal + carbTotal).toInt()

    fun getSavedImg(context: Context): Bitmap? {
        return when (isPrePopulate) {
            false -> {
                TODO("Реализовать получение картинки в случае если рецепт заранее загружен")
            }
            true -> {
                context.getBitmapByName(imgLocal)
            }
        }
    }
}
