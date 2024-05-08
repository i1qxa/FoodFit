package aps.foodfit.jyrbf.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

const val FOOD_FIT_DATA_STORE_PREFS_NAME = "food_fit_data_store_prefs_name"
val APPS_PREFS_DATA = stringPreferencesKey("apps_prefs_data")
val DS_FOOD_FIT_SAVEDLINK = stringPreferencesKey("food_fit_saved_link")
val DS_FOOD_FIT_MEDIA_SOURCE = stringPreferencesKey("media_source")
val DS_FOOD_FIT_CAMPAIGN = stringPreferencesKey("campaign")
val DS_FOOD_FIT_ADSET = stringPreferencesKey("adset")
val DS_FOOD_FIT_AF_STATUS = stringPreferencesKey("af_status")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FOOD_FIT_DATA_STORE_PREFS_NAME)
suspend fun updateAppsPrefsData(context: Context, data:String){
    context.dataStore.edit { preferences ->
        preferences[APPS_PREFS_DATA] = data
    }
}

suspend fun updateSavedLink(context: Context, newLink:String){
    context.dataStore.edit { preferences ->
        preferences[DS_FOOD_FIT_SAVEDLINK] = newLink
    }
}

suspend fun updateCampaign(context: Context, newCampaign:String){
    context.dataStore.edit { preferences ->
        preferences[DS_FOOD_FIT_CAMPAIGN] = newCampaign
    }
}

suspend fun updateMediaSource(context: Context, newMediaSource:String){
    context.dataStore.edit { preferences ->
        preferences[DS_FOOD_FIT_MEDIA_SOURCE] = newMediaSource
    }
}

suspend fun updateAdset(context: Context, newAdset:String){
    context.dataStore.edit { preferences ->
        preferences[DS_FOOD_FIT_ADSET] = newAdset
    }
}

suspend fun updateAfStatus(context: Context, newAfStatus:String){
    context.dataStore.edit { preferences ->
        preferences[DS_FOOD_FIT_AF_STATUS] = newAfStatus
    }
}

suspend fun saveFoodFitApps(context: Context, appsData:MutableMap<String,Any>){
    val mediaSource = (appsData["media_source"]?:"").toString()
    val campaign = (appsData["campaign"]?:"").toString()
    val adset = (appsData["adset"]?:"").toString()
    val afStatus = (appsData["af_status"]?:"").toString()
    updateCampaign(context, campaign)
    updateMediaSource(context, mediaSource)
    updateAdset(context, adset)
    updateAfStatus(context,afStatus)
}