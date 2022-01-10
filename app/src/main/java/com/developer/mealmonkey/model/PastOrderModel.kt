package com.developer.mealmonkey.model

data class PastOrderModel(
    val id: Int,
    val orderId: Float,
    val rName: String,
    val rArea: String,
    val itemNames: String,
    val userId: String,
    val userAddress: String,
    val deliveryStatus: String,
    val itemId: String,
    val itemQuantity: String,
    val orderedOn: Float,
    val ratings: Double,
    val totalCost: Int,
)