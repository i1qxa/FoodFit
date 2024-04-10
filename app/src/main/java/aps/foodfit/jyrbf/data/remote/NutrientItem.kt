package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class NutrientItem(
    val label:String?,
    val quantity:Double?,
    val unit:String?,
)
