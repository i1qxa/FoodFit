package aps.foodfit.jyrbf.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import aps.foodfit.jyrbf.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

const val BREAKFAST = "breakfast"
const val DINNER = "dinner"
const val LUNCH = "lunch"
const val SNACK = "snack"
const val FOOD_FIT_PREFS_NAME = "food_fit_prefs_name"
val APPS_PREFS_DATA = stringPreferencesKey("apps_prefs_data")
suspend fun Context.saveBitmap(fileName: String, bitmap: Bitmap) = withContext(Dispatchers.IO) {
    val file = File(filesDir, "${fileName}.webp")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.WEBP, 75, it)
    }
}

fun Context.getBitmapByName(fileName: String): Bitmap?{
    if(fileName.isNotEmpty()){
        val img = File(this@getBitmapByName.filesDir, fileName)
        img.inputStream().use {
            return BitmapFactory.decodeStream(it)
        }
    }else return null
}

fun Context.getBitmapByNameFromAssets(fileName: String): Bitmap?{
    return if(fileName.isNotEmpty()){
        try {
            val img = this@getBitmapByNameFromAssets.assets.open(fileName)
            BitmapFactory.decodeStream(img)
        }catch (e:Exception){
            null
        }

    }else null
}

fun FragmentManager.launchNewFragment(fragment: Fragment){
    this.beginTransaction().apply {
        replace(R.id.foodConteiner, fragment)
        addToBackStack(null)
        commit()
    }
}

fun String.firstCharToUpperCase():String{
    val strTrim = this.trim()
    val firstChar = strTrim[0].uppercaseChar()
    return "$firstChar${strTrim.removeRange(0..0)}"
}

suspend fun updateAppsPrefsData(context:Context, data:String){
    context.dataStore.edit { preferences ->
        preferences[APPS_PREFS_DATA] = data
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FOOD_FIT_PREFS_NAME)

