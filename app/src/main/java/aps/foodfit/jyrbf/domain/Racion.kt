package aps.foodfit.jyrbf.domain

data class Racion(
    val name:String,
    val imgRemote:String,
    val imgLocal:String,
    val recipeAmount:Int,
    val totalTime:Int,
    val totalWeight:Int,
    val totalKcal:Int,
    val totalProtein:Int,
    val totalFat:Int,
    val totalCarb:Int,
)
