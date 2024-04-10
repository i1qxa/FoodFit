package aps.foodfit.jyrbf.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class DigestItem(
    val label: String?,
    val tag: String?,
    val schemaOrgTag: String?=null,
    val total: Double?,
    val hasRDI: Boolean?,
    val daily: Double?,
    val unit: String?,
    val sub:List<DigestItem>?=null,
)
