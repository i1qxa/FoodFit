package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable
import aps.foodfit.jyrbf.data.remote.Hit
import aps.foodfit.jyrbf.data.remote.Links

@Serializable
data class RecipeResponseBody(
    val from:Int?,
    val to:Int?,
    val count:Int?,
    val _links: Links?,
    val hits:List<Hit>?,
)
