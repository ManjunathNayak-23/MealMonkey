package com.developer.mealmonkey.model


data class PopularRestaurantsModel(
    val imgUrl: String,
    val rName: String,
    val rRatings: Double,
    val rLatitude: Double,
    val rLongitude: Double,
    val rArea: String,
    val rType: String,
    val rId: Int,
    val rCategory: String
)


