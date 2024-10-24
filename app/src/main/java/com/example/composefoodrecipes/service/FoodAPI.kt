package com.example.composefoodrecipes.service

import com.example.composefoodrecipes.model.FoodImageModel
import retrofit2.http.GET

fun interface FoodAPI {
    @GET("api")
    suspend fun getFoodPhoto() : FoodImageModel
}