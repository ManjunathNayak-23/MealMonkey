package com.developer.mealmonkey.cartRoom

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.developer.mealmonkey.R
import com.developer.mealmonkey.room.RestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    val readAllCart: LiveData<List<Cart>>
    val totalCost: LiveData<Int>
    val totalItems: LiveData<Int>

    private val repository: CartRepository


    init {
        val cartDao = RestaurantDatabase.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)

        readAllCart = repository.readAllCart
        totalCost = repository.totalCost
        totalItems = repository.totalItems


    }

    fun addItemToCart(cart: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItemToCart(cart)
        }
    }

    fun changeItemQuantity(id: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeItemQuantity(id, quantity)
        }
    }

    fun deleteAllItems(rId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.deleteAllItems(rId)
        }

    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItemFromCart(id)
        }
    }
    fun emptyCart() {
        viewModelScope.launch(Dispatchers.IO) {

            repository.emptyCart()
        }

    }



}