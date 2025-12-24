package com.example.week8hwk.mvi

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object Error : UiState()
    object Empty : UiState()
}
