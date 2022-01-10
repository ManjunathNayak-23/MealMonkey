package com.developer.mealmonkey.cartRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developer.mealmonkey.cartRoom.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(cart: Cart)

    @Query("UPDATE cart_table SET itemQuantity = :quantity  WHERE itemId = :id;")
    suspend fun changeItemQuantity(id: Int, quantity: Int)

    @Query("SELECT SUM(singleItemCost* itemQuantity) from cart_table ")
     fun getTotalCost() : LiveData<Int>

    @Query("SELECT SUM(itemQuantity) from cart_table ")
    fun getTotalItems() : LiveData<Int>
    @Query("SELECT * from cart_table ORDER BY itemId DESC")
    fun readAllCart(): LiveData<List<Cart>>

    @Query("DELETE FROM cart_table WHERE itemId= :id")
    suspend fun deleteItem(id: Int)


    @Query("DELETE FROM cart_table WHERE rId=:rId")
    suspend fun deleteAllItems(rId:Int)


    @Query("DELETE FROM cart_table")
    suspend fun emptyCart()


}