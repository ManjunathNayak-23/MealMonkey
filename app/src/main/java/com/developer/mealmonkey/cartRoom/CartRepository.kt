package com.developer.mealmonkey.cartRoom

import androidx.lifecycle.LiveData
import com.developer.mealmonkey.cartRoom.Cart
import com.developer.mealmonkey.cartRoom.CartDao

class CartRepository(private val cartDao: CartDao) {

    suspend fun addItemToCart(cart: Cart) {
        cartDao.addItem(cart)
    }

    suspend fun deleteItemFromCart(id: Int) {
        cartDao.deleteItem(id)
    }
    suspend fun changeItemQuantity(id: Int,quantity:Int) {
        cartDao.changeItemQuantity(id,quantity)
    }
suspend fun deleteAllItems(rId:Int){
    cartDao.deleteAllItems(rId)
}
    suspend fun emptyCart(){
        cartDao.emptyCart()
    }
    val readAllCart: LiveData<List<Cart>> = cartDao.readAllCart()
    val totalCost: LiveData<Int> = cartDao.getTotalCost()
    val totalItems: LiveData<Int> = cartDao.getTotalItems()
}