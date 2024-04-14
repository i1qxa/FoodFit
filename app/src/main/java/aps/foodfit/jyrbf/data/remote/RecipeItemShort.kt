package aps.foodfit.jyrbf.data.remote

import android.graphics.Bitmap
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val FIELDS_SPLITTER = "|"
const val VALUE_SPLITTER = "~"
const val ITEM_SPLITTER = "@"
@Serializable
data class RecipeItemShort(
    val id:Int,
    val uri:String,
    val label:String?,
    val imgRegular:String?,
    val imgSmall:String?,
    val calories:Int,
    val carbs:Int,
    val fat:Int,
    val protein:Int,
    val weight:Int,
    val ingredients:List<String>?,
    val dietLabels:List<String>?,
    val mealType: List<String>?,
    val totalTime:Double?
){
    fun toRecipeDB(weight:Int, racionName:String):RecipeDB= RecipeDB(
        0,
        label?:"Название",
        imgRegular?:"",
        savedImgName,
        weight,
        calories.toDouble()/100,
        protein.toDouble()/100,
        fat.toDouble()/100,
        carbs.toDouble()/100,
        getMealType(),
        getIngredientsAsString(),
        totalTime?.toInt()?:0,
        racionName
    )

    @Contextual
    var imgBitmap:Bitmap? = null
    var savedImgName = ""

    private fun getIngredientsAsString():String{
        return try {
            Json.encodeToString(ingredients)
        } catch (e:Exception){
            ""
        }
    }

    private fun getMealType():String{
        return if (mealType!=null){
            mealType[0].ifEmpty { "" }
        }else{
            ""
        }
    }
    fun getDataForTranslate():String{
        val answerSB = StringBuilder()
        answerSB.append(id)
        answerSB.append(FIELDS_SPLITTER)
        answerSB.append(label)
        answerSB.append(FIELDS_SPLITTER)
        mealType?.forEach {
            answerSB.append(it)
            answerSB.append(VALUE_SPLITTER)
        }
        answerSB.append(FIELDS_SPLITTER)
        ingredients?.forEach {
            answerSB.append(it)
            answerSB.append(VALUE_SPLITTER)
        }
        return answerSB.toString()
    }

}
