package aps.foodfit.jyrbf.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import aps.foodfit.jyrbf.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

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

fun FragmentManager.launchNewFragment(fragment: Fragment){
    this.beginTransaction().apply {
        replace(R.id.foodConteiner, fragment)
        addToBackStack(null)
        commit()
    }
}

fun String.firstCharToUpperCase():String{
    val firstChar = this.trim()[0].uppercaseChar()
    return "$firstChar${this.removeRange(0..0)}"
}