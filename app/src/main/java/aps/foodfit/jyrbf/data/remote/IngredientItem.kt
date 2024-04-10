package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class IngredientItem(
    val text: String?,
    val quantity: Double?,
    val measure: String? = null,
    val food: String?,
    val weight: Double?,
    val foodCategory: String?,
    val foodId: String?,
    val image: String?,
)
