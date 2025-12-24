package com.example.week8hwk.ui.theme.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.week8hwk.data.model.Car
import com.example.week8hwk.mvi.UiState
import com.example.week8hwk.ui.theme.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {

    val state = viewModel.state.collectAsState().value
    var searchText by remember { mutableStateOf("") }

    // Refresh state for pull to refresh
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.uiState == UiState.Loading,
        onRefresh = { viewModel.refreshHomePage() }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            // 🔍 SEARCH BAR
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.searchCars(it)
                },
                label = { Text("Search cars...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //  CLEAR SEARCH BUTTON
            if (searchText.isNotEmpty()) {
                Button(
                    onClick = {
                        searchText = ""
                        viewModel.searchCars("")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Search")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //  HANDLE ALL UI STATES
            when (state.uiState) {
                UiState.Loading -> LoadingView()

                UiState.Success -> {
                    val cars = state.cars ?: emptyList()
                    CarList(
                        cars = cars,
                        onCarClick = { car ->
                            viewModel.selectCar(car)
                            navController.navigate("details")
                        }
                    )
                }

                UiState.Error -> ErrorView(
                    message = state.error?.message ?: "Unknown Error",
                    onRetry = { viewModel.refreshHomePage() }
                )

                UiState.Empty -> EmptyView(
                    onRefresh = { viewModel.refreshHomePage() }
                )
            }
        }

        //  PULL REFRESH ICON
        PullRefreshIndicator(
            refreshing = state.uiState == UiState.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Error: $message")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
fun EmptyView(onRefresh: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No cars found ")
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRefresh) { Text("Refresh") }
    }
}

@Composable
fun CarList(cars: List<Car>, onCarClick: (Car) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(cars) { car ->
            CarItem(car = car, onClick = { onCarClick(car) })
        }
    }
}

@Composable
fun CarItem(car: Car, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "${car.make} ${car.model}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Year: ${car.year}")
            car.carClass?.let { Text("Class: $it") }
        }
    }
}
