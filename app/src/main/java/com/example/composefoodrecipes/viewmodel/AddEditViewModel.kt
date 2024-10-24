package com.example.composefoodrecipes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composefoodrecipes.model.FoodImageModel
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.repository.FoodRepository
import com.example.composefoodrecipes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    //state ile çalışmak
    var foodImage = mutableStateOf<FoodImageModel?>(null)
    private var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var selectedFood = mutableStateOf<FoodModel?>(null)

    init {
        fetchFoodImage()
    }

    private fun fetchFoodImage() {
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getFoodPhoto()) {
                is Resource.Success -> {
                    result.data?.foodName = extractFoodType(result.data?.foodImageUrl ?: "")
                    foodImage.value = result.data
                    selectedFood.value =
                        FoodModel(0, true, "", foodImage.value?.foodImageUrl ?: "", "")
                    errorMessage.value = ""
                    isLoading.value = false
                }

                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }
        }
    }

    suspend fun suspendFetchFoodImage() {
        isLoading.value = true
        when (val result = repository.getFoodPhoto()) {
            is Resource.Success -> {
                result.data?.foodName = extractFoodType(result.data?.foodImageUrl ?: "")
                foodImage.value = result.data
                selectedFood.value = FoodModel(0, true, "", foodImage.value?.foodImageUrl ?: "", "")
                errorMessage.value = ""
                isLoading.value = false
            }

            is Resource.Error -> {
                errorMessage.value = result.message!!
                isLoading.value = false
            }

            is Resource.Loading -> TODO()
        }

    }

    private fun extractFoodType(url: String): String {
        val parts = url.split('/')
        val foodType = parts[3]
        return foodType.replace("-", " ").replaceFirstChar { it.uppercaseChar() }
    }

    fun saveFood(foodModel: FoodModel?, isSaved: (msg: String, isSaved: Boolean) -> Unit) {
        foodModel?.let {
            if (foodModel.foodId == 0) {
                viewModelScope.launch {
                    repository.insertFood(foodModel)
                    isSaved.invoke("Food saved", true)
                }
            } else {
                viewModelScope.launch {
                    repository.updateFood(foodModel)
                    isSaved.invoke("Food updated", true)
                }
            }
            return
        }
        isSaved.invoke("Food not saved", false)
    }

}