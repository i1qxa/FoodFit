package aps.foodfit.jyrbf.ui.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UrlFoodFit(
    @SerialName("url")
    val url:String
)


