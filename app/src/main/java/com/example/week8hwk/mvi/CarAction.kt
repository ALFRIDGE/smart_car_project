package com.example.week8hwk.mvi

import com.example.week8hwk.data.model.Car

sealed class CarAction {
    data object FetchCar: CarAction()

    data class FetchCarSuccess(val cars: List<Car>): CarAction()

    data class FetchCarError(val error: String): CarAction()

    data class UpdateSelectedCar(val car: Car?): CarAction()


}