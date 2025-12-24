package com.example.week8hwk.data.network

import com.example.week8hwk.data.model.Car
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("cars")
    suspend fun getCars(
        @Query("make") make: String = "toyota"
    ): List<Car>
}
