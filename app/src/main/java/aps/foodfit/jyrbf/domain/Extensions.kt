package aps.foodfit.jyrbf.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun Context.saveBitmap(fileName: String, bitmap: Bitmap) = withContext(Dispatchers.IO) {
    val file = File(filesDir, fileName)
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