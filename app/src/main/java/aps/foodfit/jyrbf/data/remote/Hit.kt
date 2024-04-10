package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hit(
    val recipe: RecipeItem?,
    @SerialName("_links")
    val links: LinkHit?,
)
