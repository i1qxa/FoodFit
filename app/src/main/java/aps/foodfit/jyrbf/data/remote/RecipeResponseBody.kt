package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseBody(
    val from:Int?,
    val to:Int?,
    val count:Int?,
    val _links: Links?,
    val hits:List<Hit>?,
)
