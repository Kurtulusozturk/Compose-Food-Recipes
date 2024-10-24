package com.example.composefoodrecipes.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.util.Constants.boldFont
import com.example.composefoodrecipes.viewmodel.AddEditViewModel
import kotlinx.serialization.Serializable

@Serializable
data class RouteAddEditScreen(val foodModel: FoodModel? = null)

@Composable
fun AddEditScreen(
    navController: NavController,
    foodModel: FoodModel?,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val food by remember { mutableStateOf(foodModel) }
    var foodName by remember { mutableStateOf(foodModel?.foodName ?: "") }
    var recipe by remember { mutableStateOf(foodModel?.recipe ?: "") }
    Scaffold(
        topBar = {
            Text(
                if (foodModel?.foodId == 0) "Add" else "Edit",
                modifier = Modifier.padding(top = 30.dp, start = 20.dp, bottom = 20.dp),
                fontFamily = FontFamily(boldFont),
                fontSize = 24.sp,
                color = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.size(60.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    if (foodName.isNotEmpty() && recipe.isNotEmpty()){
                        viewModel.saveFood(food) { msg, isSaved ->
                            Toast.makeText(navController.context, msg, Toast.LENGTH_SHORT).show()
                            // with route https://www.youtube.com/watch?v=uc7mF0HSnng
                            // with serialization https://www.youtube.com/watch?v=qBxaZ071N0c
                            if (isSaved)
                                navController.navigate(RouteListScreen) {
                                    popUpTo(RouteListScreen::class.java.name) {
                                        inclusive = true
                                    }
                                }
                        }
                    }else{
                        Toast.makeText(navController.context,"Fill the required fields.",Toast.LENGTH_SHORT).show()
                    }
                }) {
                Icon(Icons.Filled.Done, "Floating action button.")
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.tertiary
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = foodModel?.foodImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp) //xml-margin
                    .align(Alignment.CenterHorizontally)
                    .wrapContentWidth(Alignment.End)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(3.dp) //xml-padding
                    .clip(RoundedCornerShape(10.dp))
                    .size(200.dp, 120.dp),
                loading = {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            )
            OutlinedTextField(label = {
                Text("Food Name")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                value = foodName,
                onValueChange = { text ->
                    foodName = text
                    food?.foodName = text
                })

            OutlinedTextField(label = {
                Text("Recipe")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp, bottom = 16.dp)
                    .height(300.dp),
                value = recipe,
                onValueChange = { text ->
                    recipe = text
                    food?.recipe = text
                })

        }
    }

}