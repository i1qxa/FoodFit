package aps.foodfit.jyrbf.data.remote


data class RecipeTranslatedSubData(
    val id:Int,
    val name:String,
//    val diets:List<String>,
//    val ingredients:List<String>,
){
    companion object{
        fun decodeFromString(encodedString:String): RecipeTranslatedSubData?{
            try {
//                val tmpDiets = mutableListOf<String>()
//                val tmpIngredients = mutableListOf<String>()
                val encodeStringAsList = encodedString.split(FIELDS_SPLITTER)
                val tmpId = encodeStringAsList[0].trim().toInt()
                val tmpName=encodeStringAsList[1]
//                encodeStringAsList[2].split(VALUE_SPLITTER).forEach { dietItem ->
//                    tmpDiets.add(dietItem)
//                }
//                encodeStringAsList[3].split(VALUE_SPLITTER).forEach { ingredientItem ->
//                    tmpIngredients.add(ingredientItem)
//                }
//                return RecipeTranslatedSubData(tmpId, tmpName, tmpDiets,tmpIngredients)
                return RecipeTranslatedSubData(tmpId, tmpName)
            }catch (e:NullPointerException){
                return null
            }catch (e:Exception){
                e.message
                return null
            }
        }
    }
}
