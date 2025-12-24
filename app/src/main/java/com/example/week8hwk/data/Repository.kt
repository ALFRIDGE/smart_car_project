package com.example.week8hwk.data

import com.example.week8hwk.data.database.AppDatabase
import com.example.week8hwk.data.model.Car
import com.example.week8hwk.data.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val apiService: ApiService,
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCars(): List<Car> = withContext(dispatcher) {
        val local = db.dao().getAllCars()
        if (local.isNotEmpty())
            return@withContext local

        val remote = apiService.getCars("toyota")
        val mapped = remote.map {
            Car(
                make = it.make,
                model = it.model,
                year = it.year,
                carClass = it.carClass,
                transmission = it.transmission,
                drive = it.drive
            )
        }
        db.dao().insertCars(mapped)
        return@withContext mapped
    }

    suspend fun searchCars(query: String): List<Car> = withContext(dispatcher) {
        val local = db.dao().searchCars(query)
        if (local.isNotEmpty())
            return@withContext local

        val remote = apiService.getCars(query)
        val mapped = remote.map {
            Car(
                make = it.make,
                model = it.model,
                year = it.year,
                carClass = it.carClass,
                transmission = it.transmission,
                drive = it.drive
            )
        }

        if (mapped.isNotEmpty()) db.dao().insertCars(mapped)
        return@withContext mapped
    }
    suspend fun refreshCarsFromApi(make: String) = withContext(dispatcher) {
        val remoteCars = apiService.getCars(make)
        if (remoteCars.isNotEmpty()) {
            db.dao().insertCars(remoteCars)
        }
}}




