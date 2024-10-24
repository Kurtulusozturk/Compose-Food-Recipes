package com.example.composefoodrecipes.repository

import android.content.Context
import com.example.composefoodrecipes.R
import com.example.composefoodrecipes.model.FoodImageModel
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.service.FoodAPI
import com.example.composefoodrecipes.service.FoodDao
import com.example.composefoodrecipes.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class FoodRepository @Inject constructor(
    private val api: FoodAPI, @ApplicationContext context: Context, private val dao: FoodDao
) {
    private val context = context

    suspend fun getFoodPhoto(): Resource<FoodImageModel> {
        val response = try {
            api.getFoodPhoto()
        } catch (e: Exception) {
            return Resource.Error(context.getString(R.string.msg_try_later))
        }
        return Resource.Success(response)
    }

    suspend fun getAllFoods(): List<FoodModel> {
        return dao.getAllFoods()
    }

    suspend fun insertFood(foodModel: FoodModel): Long {
        return dao.insert(foodModel)
    }

    suspend fun updateFood(foodModel: FoodModel) {
        return dao.updateFood(foodModel.foodName, foodModel.recipe, foodModel.foodId)
    }

    suspend fun deleteFood(foodId: Int) {
        return dao.deleteFood(foodId)
    }
}