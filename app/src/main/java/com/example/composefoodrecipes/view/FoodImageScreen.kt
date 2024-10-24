package com.example.composefoodrecipes.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.composefoodrecipes.util.Constants.boldFont
import com.example.composefoodrecipes.util.Constants.mediumFont
import com.example.composefoodrecipes.viewmodel.AddEditViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object RouteFoodImageScreen

@Composable
fun FoodImageScreen(navController: NavController, viewModel: AddEditViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        Text(
            "Food Image",
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
                    if (viewModel.selectedFood.value?.foodImageUrl.isNullOrEmpty()) {
                        Toast.makeText(
                            navController.context,
                            "Fotoğraf bulunamadı lütfen daha sonra tekrar deneyin.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate(RouteAddEditScreen(viewModel.selectedFood.value))
                    }
                }) {
                Icon(Icons.Filled.Add, "Floating action button.")
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubcomposeAsyncImage(
                model = viewModel.foodImage.value?.foodImageUrl,
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
            OutlinedButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.suspendFetchFoodImage()
                }
            }, enabled = !viewModel.isLoading.value) {
                Text("Change", fontFamily = FontFamily(mediumFont))
            }
        }
    }
}