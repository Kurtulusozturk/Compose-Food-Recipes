package com.example.composefoodrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.model.FoodModelNavType
import com.example.composefoodrecipes.ui.theme.ComposeFoodRecipesTheme
import com.example.composefoodrecipes.view.AddEditScreen
import com.example.composefoodrecipes.view.FoodImageScreen
import com.example.composefoodrecipes.view.ListScreen
import com.example.composefoodrecipes.view.RouteAddEditScreen
import com.example.composefoodrecipes.view.RouteFoodImageScreen
import com.example.composefoodrecipes.view.RouteListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf


/*
@AndroidEntryPoint, Hilt'in Android bileşenlerine (Activity, Fragment, View, Service, BroadcastReceiver) bağımlılık enjekte etmesini sağlayan bir anotasyondur.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeFoodRecipesTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = RouteListScreen){
                    //Ekranlarımızı tanımlıyoruz
                    composable<RouteListScreen>{
                        ListScreen(navController)
                    }
                    composable<RouteFoodImageScreen> {
                        FoodImageScreen(navController)
                    }
                    //Argümanlı ekranlar için
                    composable<RouteAddEditScreen>(
                        typeMap = mapOf(
                            typeOf<FoodModel?>() to FoodModelNavType.foodType
                            //diğer custom modeller için devamında tanımlanmalı
                        )
                    ){
                        val argument = it.toRoute<RouteAddEditScreen>()
                        //AddEdit Screen
                        //Remember metodu Recomposition yapılırken,
                        // tüm UI yeniden çizilirken değişkende değişiklik olamaması için kullanılır
                        AddEditScreen(navController,argument.foodModel)
                    }
                }
            }
        }
    }
}