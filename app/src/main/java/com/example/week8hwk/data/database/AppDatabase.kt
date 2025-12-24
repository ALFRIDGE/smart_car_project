package com.example.week8hwk.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.week8hwk.data.Dao
import com.example.week8hwk.data.model.Car


@Database(entities = [Car::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao
}



