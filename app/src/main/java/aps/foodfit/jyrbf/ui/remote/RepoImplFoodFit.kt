package aps.foodfit.jyrbf.ui.remote

import android.content.Context
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_ADSET
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_AF_STATUS
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_CAMPAIGN
import aps.foodfit.jyrbf.domain.DS_FOOD_FIT_MEDIA_SOURCE
import aps.foodfit.jyrbf.domain.dataStore
import aps.foodfit.jyrbf.domain.updateSavedLink
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException

class RepoImplFoodFit(private val context: Context) {

    fun getLinkFoodFit() {
        CoroutineScope(Dispatchers.IO).launch {
            val linkData = LinkDataFoodFit()
            val appId = context.packageName
            val deviceId = AppsFlyerLib.getInstance().getAppsFlyerUID(context).toString()
            linkData.appsId = deviceId
            linkData.appPackage = appId

//            delay(100)
            context.dataStore.data.collect {
                linkData.apply {
                    appsMediaSource = it[DS_FOOD_FIT_MEDIA_SOURCE] ?: ""
                    appsCampaignName = it[DS_FOOD_FIT_CAMPAIGN] ?: ""
                    appsAdsetName = it[DS_FOOD_FIT_ADSET] ?: ""
                    appsAfStatus = it[DS_FOOD_FIT_AF_STATUS] ?: ""
                    androidAdvID = AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
                }
                val link = getLinkByAppsData(linkData.getLink())
                if (link != null) {
                    updateSavedLink(context, link)
                }
            }
//            linkData.androidAdvID =
//                AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
//            val link = getLinkByAppsData(linkData.getLink())
//            if (link != null) {
//                updateSavedLink(context, link)
//            }
        }
    }

    private fun getLinkByAppsData(s: String): String? {
        var responseLink: String? = null
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(s)
            .get()
            .addHeader("accept", "application/json")
            .build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body
            val responseString = responseBody?.string().toString()
            val link = decodeJsonFoodFit(responseString)
            if (link != null) {
                val newRequest = Request.Builder()
                    .url(link.url)
                    .get()
                    .addHeader("accept", "application/json")
                    .build()
                val lastResponse = client.newCall(newRequest).execute()
                if (lastResponse.isSuccessful) {
                    val networkResponse = lastResponse.networkResponse.toString()
                    val redirRegex = Regex("""(https://[a-z\d?=&{}_./-]*)""")
                    val redirect = redirRegex.find(networkResponse)?.value ?: ""
                    if (redirect.isNotEmpty()) {
                        val redirNew = redirect.dropLast(1)
                        responseLink = if (redirNew.contains("localhost")) null
                        else redirNew
                    }
                }
            }


        } else {
        }
        return responseLink
    }

    private fun decodeJsonFoodFit(preFoodFitData: String): UrlFoodFit? {
        return try {
            Json.decodeFromString<UrlFoodFit>(preFoodFitData)
        } catch (e: Exception) {
            null
        } catch (e: JSONException) {
            null
        }
    }

}