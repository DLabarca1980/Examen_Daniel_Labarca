package com.example.examen_daniel_labarca.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PlaceEntity::class], version = 1)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDao() : PlaceDao

    companion object {
        @Volatile
        private var BASE_DATOS : AppDatabase? = null

        fun getInstance (context: Context) : AppDatabase {
            return BASE_DATOS ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "Places.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { BASE_DATOS = it}
            }
        }
    }
}
