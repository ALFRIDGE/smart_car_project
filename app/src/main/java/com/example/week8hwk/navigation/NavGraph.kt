package com.example.week8hwk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.week8hwk.ui.theme.screens.DetailsScreen
import com.example.week8hwk.ui.theme.viewmodel.MainViewModel
import com.example.week8hwk.ui.theme.screens.HomeScreen


@Composable
fun NavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("details") { DetailsScreen(viewModel) }
    }
}
