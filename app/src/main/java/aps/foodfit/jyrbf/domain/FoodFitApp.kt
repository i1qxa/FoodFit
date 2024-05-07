package aps.foodfit.jyrbf.domain

import android.app.Application
import android.content.Context
import aps.foodfit.jyrbf.data.local.recipe.RecipeDataBase
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val FOOD_FIT_APPSFLYER_KEY = "wacAvoJ7QS7CgrtJTJsX4V"


class FoodFitApp:Application() {


    override fun onCreate() {
        super.onCreate()
        val db = RecipeDataBase.getInstance(this).recipeDao()
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(anyMutableMap: MutableMap<String, Any>?) {
                if (anyMutableMap != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (db.getAmountOfRecipes() > -1) {
//                            FoodFitAppsData.foodFitAppsData.postValue(anyMutableMap)
                            updateAppsPrefsData(this@FoodFitApp,
                                (anyMutableMap["af_status"]?:"").toString()
                            )
                            val a = anyMutableMap
                            val b = a
                        }
                    }
                }
            }

            override fun onConversionDataFail(error: String?) {

            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
            }

            override fun onAttributionFailure(error: String?) {

            }
        }

        AppsFlyerLib.getInstance().apply {
            init(FOOD_FIT_APPSFLYER_KEY, conversionDataListener, this@FoodFitApp)
            start(this@FoodFitApp, FOOD_FIT_APPSFLYER_KEY, object :
                AppsFlyerRequestListener {
                override fun onSuccess() {
                }

                override fun onError(p0: Int, p1: String) {

                }
            }
            )
        }
    }

}