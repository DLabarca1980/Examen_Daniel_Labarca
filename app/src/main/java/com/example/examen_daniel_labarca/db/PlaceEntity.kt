package com.example.examen_daniel_labarca.db

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PlaceEntity(

    @PrimaryKey(autoGenerate = true)
    val uid : Int,

    @ColumnInfo
    var place : String,

    @ColumnInfo
    var imgRef : Bitmap?,

    @ColumnInfo
    var longitud : Double?,

    @ColumnInfo
    var latitud : Double?,

    @ColumnInfo
    var order : Int,

    @ColumnInfo
    var price : Double,

    @ColumnInfo
    var movePrice : Double,

    @ColumnInfo
    var comments : String?
)
