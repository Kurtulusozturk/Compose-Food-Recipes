package com.example.composefoodrecipes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodImageModel(
    @SerializedName("image") var foodImageUrl: String? = null,
    var foodName: String = ""
) : Serializable