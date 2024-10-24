package com.example.composefoodrecipes.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.composefoodrecipes.model.FoodModel

@Dao
interface FoodDao {
    @Insert
    suspend fun insert(food: FoodModel): Long

    @Query("SELECT * FROM FoodModel WHERE isActive = 1")
    suspend fun getAllFoods(): List<FoodModel>

    @Query("SELECT * FROM FoodModel WHERE foodId = :foodId AND isActive = 1")
    suspend fun getFood(foodId: Int): FoodModel

    @Query("UPDATE FoodModel SET isActive = 0  WHERE foodId = :foodId")
    suspend fun deleteFood(foodId: Int)

    @Query("UPDATE FoodModel Set foodName =:foodName, recipe= :recipe WHERE foodId = :foodId")
    suspend fun updateFood (foodName : String, recipe: String, foodId: Int)
}