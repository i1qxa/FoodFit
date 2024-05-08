package aps.foodfit.jyrbf.ui.remote

import aps.foodfit.jyrbf.domain.ADRESS_FOOD_FIT
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDataFoodFit(
    @SerialName("advertiser_id")
    var androidAdvID: String? = null,
    @SerialName("app_package")
    var appPackage: String? = null,
    var appsMediaSource: String? = null,
    var appsCampaignName: String? = null,
    var appsAdsetName: String? = null,
    var appsAfStatus: String? = null,
    var appsId: String? = null,
    var domain: String = convertAdress(ADRESS_FOOD_FIT)
) {
    fun getLink(): String {
        val sb = StringBuilder()
        with(sb) {
            append("https://")
            append(domain)
            append("/config/version/4/?package=")
            append(appPackage)
            append("&adv_id=")
            append(androidAdvID)
            append("&utm_source=")
            append(appsMediaSource?:"")
            append("&utm_campaign=")
            append(appsCampaignName?:"")
            append("&utm_content=")
            append(appsAdsetName?:"")
            append("&utm_medium=")
            append(appsAfStatus?:"")
            append("&utm_term=")
            append(appsId?:"")
            return sb.toString()
        }
    }
}
fun convertAdress(inputStr:String):String{
    val tmpSB = StringBuilder()
    inputStr.forEach{
        tmpSB.append(Char(it.code -2))
    }
    return tmpSB.toString()
}

