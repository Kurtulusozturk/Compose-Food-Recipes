package com.example.composefoodrecipes.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(application: Application, private var repository: FoodRepository) : BaseViewModel(application) {
    var list = mutableStateOf<List<FoodModel>>(listOf())

    init {
        getData()
    }

    private fun getData() {
        launch {
            list.value = repository.getAllFoods()
        }
    }

    fun deleteFood(foodId: Int) {
        launch {
            repository.deleteFood(foodId)
            getData()
        }
    }

}