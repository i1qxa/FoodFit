package aps.foodfit.jyrbf.data.remote

import android.graphics.Bitmap
import aps.foodfit.jyrbf.data.local.recipe.RecipeDB
import aps.foodfit.jyrbf.domain.BREAKFAST
import aps.foodfit.jyrbf.domain.DINNER
import aps.foodfit.jyrbf.domain.LUNCH
import aps.foodfit.jyrbf.domain.SNACK
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
    fun toRecipeDB(racionName:String):RecipeDB= RecipeDB(
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
        racionName,
        false,
        uri
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

    private fun getMealType():Int{
        return if (mealType!=null){
            getMealTypeAsInt(mealType[0].ifEmpty { "" })
        }else{
            5
        }
    }
    private fun getMealTypeAsInt(mealTypeAsStr:String):Int{
        val mealTypeSplitted = mealTypeAsStr.split("/")[0]
        return when(mealTypeSplitted){
            BREAKFAST -> 1
            LUNCH ->2
            DINNER ->3
            SNACK ->4
            else ->5
        }
    }
    fun getDataForTranslate():String{
        val answerSB = StringBuilder()
        answerSB.append(id)
        answerSB.append(FIELDS_SPLITTER)
        answerSB.append(label)
        return answerSB.toString()
    }


    val proteinPercent:Int
        get() = ((protein.toDouble()/totalNuntrientsMass)*100).toInt()

    val fatPercent:Int
        get() = ((fat.toDouble()/totalNuntrientsMass)*100).toInt()

    val carbPercent:Int
        get() = ((carbs.toDouble()/totalNuntrientsMass)*100).toInt()
    private val totalNuntrientsMass:Int
        get() = (protein + fat + carbs)

}
