package com.example.week8hwk.mvi

import com.example.week8hwk.data.model.Car

data class CarState(
    val uiState: UiState = UiState.Loading,
    val selectedCar: Car? = null,
    val cars: List<Car>? = null,
    val error: Exception? = null
)
