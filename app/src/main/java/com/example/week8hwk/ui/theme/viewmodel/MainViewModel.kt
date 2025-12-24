package com.example.week8hwk.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week8hwk.data.Repository
import com.example.week8hwk.data.model.Car
import com.example.week8hwk.mvi.CarAction
import com.example.week8hwk.mvi.CarReducer
import com.example.week8hwk.mvi.CarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {


    private val _state = MutableStateFlow(CarState())


    val state: StateFlow<CarState> = _state.asStateFlow()


    fun fireAction(action: CarAction) {
        _state.value = CarReducer(_state.value, action)
    }


    fun loadCars() {
        viewModelScope.launch {
            fireAction(CarAction.FetchCar)

            try {
                val cars = repository.getCars()
                fireAction(CarAction.FetchCarSuccess(cars))
            } catch (e: Exception) {
                fireAction(CarAction.FetchCarError(e.message ?: "Unknown Error"))
            }
        }
    }


    fun refreshHomePage() {
        viewModelScope.launch {
            fireAction(CarAction.FetchCar)

            try {
                repository.refreshCarsFromApi("toyota")
                val cars = repository.getCars()
                fireAction(CarAction.FetchCarSuccess(cars))
            } catch (e: Exception) {
                fireAction(CarAction.FetchCarError(e.message ?: "Unknown Error"))
            }
        }

        loadCars()
    }


    fun searchCars(query: String) {
        viewModelScope.launch {
            fireAction(CarAction.FetchCar) // show loading

            try {
                val cars = repository.searchCars(query)
                fireAction(CarAction.FetchCarSuccess(cars))
            } catch (e: Exception) {
                fireAction(CarAction.FetchCarError(e.message ?:"Unknown Error"))
            }
        }
    }


    fun selectCar(car: com.example.week8hwk.data.model.Car) {
        fireAction(CarAction.UpdateSelectedCar(car))
    }


    init {
        loadCars()


    }

    fun refreshDetailsPage(car: Car) {
        fireAction(CarAction.UpdateSelectedCar(car))
    }
}







