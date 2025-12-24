package com.example.week8hwk.mvi

fun CarReducer(oldState: CarState, action: CarAction): CarState {
    return when (action) {

        is CarAction.FetchCar -> {
            oldState.copy(
                uiState = UiState.Loading,
                error = null
            )
        }

        is CarAction.FetchCarSuccess -> {
            oldState.copy(
                uiState = UiState.Success,
                cars = action.cars,
                error = null
            )
        }

        is CarAction.FetchCarError -> {
            oldState.copy(
                uiState = UiState.Error,
                error = Exception(action.error))
        }

        is CarAction.UpdateSelectedCar -> {
            oldState.copy(
                selectedCar = action.car
            )
        }
    }
}
