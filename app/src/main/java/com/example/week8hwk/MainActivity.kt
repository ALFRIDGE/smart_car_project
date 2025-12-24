package com.example.week8hwk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.week8hwk.data.Repository
import com.example.week8hwk.data.database.AppDatabase
import com.example.week8hwk.data.network.RetrofitClient
import com.example.week8hwk.navigation.NavGraph
import com.example.week8hwk.ui.theme.Week8HwkTheme
import com.example.week8hwk.ui.theme.viewmodel.MainViewModel
import com.example.week8hwk.ui.theme.viewmodel.MainViewModelFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "cars_db"
        ).fallbackToDestructiveMigration()
            .build()

        val repository = Repository(
            RetrofitClient.api,
            db = db)
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setContent {
            Week8HwkTheme {
                val navController = rememberNavController()
                NavGraph(navController, viewModel)
                }
            }
        }
    }

