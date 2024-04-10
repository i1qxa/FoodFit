package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    @SerialName("THUMBNAIL")
    val thumbnail: ImgItem?,
    @SerialName("SMALL")
    val small: ImgItem?,
    @SerialName("REGULAR")
    val regular: ImgItem?,
    @SerialName("LARGE")
    val large: ImgItem? = null,
)
