package com.udacity.asteroidradar.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(version = 1,entities = [Asteroid::class])
abstract class DataBaseClass : RoomDatabase(){

     abstract val Dao: DB_Dao


    companion object {
        @Volatile
        private lateinit var instance: DataBaseClass

        fun getInstance(context: Context): DataBaseClass {
            synchronized(DataBaseClass::class.java) {
                if(!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseClass::class.java,
                        "DB1")
                        .build()
                }
            }
            return instance
        }

    }
}