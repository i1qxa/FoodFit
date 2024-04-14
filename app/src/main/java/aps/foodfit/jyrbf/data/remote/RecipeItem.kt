package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RecipeItem(
    val uri: String? = null,
    val label: String? = null,
    val image: String? = null,
    val images: Images? = null,
    val source: String? = null,
    val url: String? = null,
    val shareAs: String? = null,
    val yield: Double? = null,
    val dietLabels: List<String>? = null,
    val healthLabels: List<String>? = null,
    val cautions: List<String>? = null,
    val ingredientLines: List<String>? = null,
    val ingredients: List<IngredientItem>? = null,
    val calories: Float? = null,
    val totalCO2Emissions: Float? = null,
    val co2EmissionsClass: String? = null,
    val totalWeight: Float? = null,
    val totalTime: Double? = null,
    val cuisineType: List<String>?,
    val mealType: List<String>? = null,
    val dishType: List<String>? = null,
    val totalNutrients: TotalNutrient? = null,
    val totalDaily: TotalNutrient? = null,
    val digest: List<DigestItem>? = null
) {

    fun getRecipeShort(newId:Int) = RecipeItemShort(
        newId,
        uri ?: "",
        label,
        images?.regular?.url,
        images?.small?.url,
        (calories ?: (1.0f)).toInt(),
        (totalNutrients?.chocdf?.quantity ?: 1.0F).toInt(),
        (totalNutrients?.fat?.quantity ?: 1.0F).toInt(),
        (totalNutrients?.procnt?.quantity ?: 1.0F).toInt(),
        (totalWeight ?: 1).toInt(),
        ingredientLines,
        dietLabels,
        mealType,
        totalTime

    )

}
