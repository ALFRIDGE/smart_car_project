package com.example.week8hwk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val carModel: String,
    val reviewer: String,
    val rating: Int,
    val comment: String
)
