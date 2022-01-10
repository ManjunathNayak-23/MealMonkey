package com.developer.mealmonkey.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurant_table")
data class Restaurant(

    val imgUrl: String,
    val rName: String,
    val rRatings: Double,
    val rLatitude: Double,
    val rLongitude: Double,
    val rArea: String,
    val rType: String,
    @PrimaryKey
    val rId: Int,
    val rCategory: String
)