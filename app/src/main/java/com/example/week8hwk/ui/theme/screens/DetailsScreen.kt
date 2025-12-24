package com.example.week8hwk.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week8hwk.mvi.CarAction
import com.example.week8hwk.ui.theme.viewmodel.MainViewModel

@Composable
fun DetailsScreen(vm: MainViewModel) {


    val user = vm.state.collectAsState().value.selectedCar


    LaunchedEffect(vm){
        vm.refreshDetailsPage(car = user!!)
    }


    if (user !=null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Car Details",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(text = "Make: ${user.make}")
            Text(text = "Model: ${user.model}")
            Text(text = "Year: ${user.year}")
            Text(text = "Class: ${user.carClass?: "N/A"}")
            Text(text = "Transmission: ${user.transmission ?: "N/A"}")
            Text(text = "Drive: ${user.drive ?: "N/A"}")
        }

    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading car details...")
        }
    }
}

