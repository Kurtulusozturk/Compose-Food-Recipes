package com.example.composefoodrecipes.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.composefoodrecipes.model.FoodModel
import com.example.composefoodrecipes.ui.theme.Gray
import com.example.composefoodrecipes.util.Constants.boldFont
import com.example.composefoodrecipes.util.Constants.lightFont
import com.example.composefoodrecipes.util.Constants.mediumFont
import com.example.composefoodrecipes.viewmodel.ListViewModel
import kotlinx.serialization.Serializable

@Serializable
object RouteListScreen

@Composable
fun ListScreen(navController: NavController, viewModel: ListViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            Text(
                "My Recipes",
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
                    navController.navigate(RouteFoodImageScreen)
                }) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.tertiary
    ) { innerPadding ->
        if (viewModel.list.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please Add a Recipe",
                    color = Gray,
                    fontFamily = FontFamily(mediumFont),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                viewModel.list.value.forEach { listItem ->
                    item {
                        listItemView(navController, listItem, viewModel)
                        if (listItem == viewModel.list.value.last()){
                            Column(
                                Modifier
                                    .padding(bottom = 50.dp)
                                    .size(50.dp, 50.dp) ) { }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun listItemView(navController: NavController, listItem: FoodModel, viewModel: ListViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    Card(colors = CardDefaults.cardColors(
        containerColor = Color.White, contentColor = Color.Black
    ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
            .combinedClickable(onClick = {
                navController.navigate(RouteAddEditScreen(foodModel = listItem))
            }, onLongClick = {
                showDialog = true
            })
    ) {
        Row(Modifier.padding(10.dp, 5.dp)) {
            Column(modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.5f)) {
                Text(
                    listItem.foodName,
                    fontFamily = FontFamily(mediumFont),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    listItem.recipe,
                    fontFamily = FontFamily(lightFont),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            SubcomposeAsyncImage(
                model = listItem.foodImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth(Alignment.End)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(3.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(100.dp, 70.dp),
                loading = {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm") },
            text = { Text("Are you sure you want to delete this recipe?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteFood(foodId = listItem.foodId)
                        showDialog = false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}