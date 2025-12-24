package com.example.week8hwk.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.week8hwk.data.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE carModel = :model")
    suspend fun getReviewsForModel(model: String): List<Review>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)
}
