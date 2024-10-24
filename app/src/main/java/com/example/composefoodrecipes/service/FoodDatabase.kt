package com.example.composefoodrecipes.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composefoodrecipes.model.FoodModel

@Database(entities = [FoodModel::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        //volatile farklı tread lerde görünür hale getirir
        @Volatile
        private var instance: FoodDatabase? = null


        private val lock = Any()
        fun getDatabase(context: Context): FoodDatabase{
            return instance ?: synchronized(lock){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                ).build()
                this.instance = instance
                instance
            }
        }
    }

}