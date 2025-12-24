package com.example.week8hwk.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.week8hwk.data.model.Car

@Dao
interface Dao {
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(cars: List<Car>)

    @Query("SELECT * FROM cars WHERE make LIKE '%' || :query || '%' OR model LIKE '%' || :query || '%'")
    suspend fun searchCars(query: String): List<Car>

  }


