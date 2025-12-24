package com.example.week8hwk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val make: String,
    val model: String,
    val year: Int,
    val carClass: String?,
    val transmission: String?,
    val drive: String?
)



