//package aps.foodfit.jyrbf.data.remote
//
//import kotlinx.serialization.Serializable
//
const val FIELDS_SPLITTER = "|"
const val VALUE_SPLITTER = "~"
const val ITEM_SPLITTER = "@"
//@Serializable
//data class RecipeItemShort(
//    val id:Int,
//    val uri:String,
//    val label:String?,
//    val imgRegular:String?,
//    val imgSmall:String?,
//    val calories:Int,
//    val carbs:Int,
//    val fat:Int,
//    val protein:Int,
//    val weight:Int,
//    val ingredients:List<String>?,
//    val dietLabels:List<String>?,
//){
//    fun toFoodDB(weight:Int, date:String, dateInMils:Long):FoodDB=FoodDB(
//        0,
//        label?:"Название",
//        imgRegular?:"",
//        date,
//        dateInMils,
//        weight,
//        calories.toDouble()/100,
//        protein.toDouble()/100,
//        fat.toDouble()/100,
//        carbs.toDouble()/100,
//        false
//    )
//
//    fun getDataForTranslate():String{
//        val answerSB = StringBuilder()
//        answerSB.append(id)
//        answerSB.append(FIELDS_SPLITTER)
//        answerSB.append(label)
//        answerSB.append(FIELDS_SPLITTER)
//        dietLabels?.forEach {
//            answerSB.append(it)
//            answerSB.append(VALUE_SPLITTER)
//        }
//        answerSB.append(FIELDS_SPLITTER)
//        ingredients?.forEach {
//            answerSB.append(it)
//            answerSB.append(VALUE_SPLITTER)
//        }
//        return answerSB.toString()
//    }
//
//}
