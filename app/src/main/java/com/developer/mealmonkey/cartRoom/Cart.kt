package com.developer.mealmonkey.cartRoom

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(

    @PrimaryKey
    val itemId:Int,
    val itemName:String,
    val itemIsVeg:String,
    val rImgUrl: String,
    val rName: String,
    val rArea: String,
    val rId: Int,
    val itemQuantity: Int,
    val singleItemCost: Int,
)
