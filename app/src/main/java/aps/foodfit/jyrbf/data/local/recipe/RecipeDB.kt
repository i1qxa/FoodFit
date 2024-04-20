package aps.foodfit.jyrbf.data.local.recipe

import android.content.Context
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import aps.foodfit.jyrbf.domain.getBitmapByName
import kotlinx.serialization.Contextual

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
    val isPrePopulate: Boolean
) {
    val kcalTotal: Double
        get() = kcalPerGram * weightInGrams
    val proteinTotal: Double
        get() = proteinPerGram * weightInGrams
    val fatTotal: Double
        get() = fatPerGram * weightInGrams
    val carbTotal: Double
        get() = carbPerGram * weightInGrams

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
