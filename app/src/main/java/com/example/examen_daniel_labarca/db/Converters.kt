package com.example.examen_daniel_labarca.db

import android.graphics.Bitmap
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverters
    fun fromBitmap(bitmap: Bitmap) : ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverters
    fun toBitmap(byteArray: ByteArray) : Bitmap {
        return android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}