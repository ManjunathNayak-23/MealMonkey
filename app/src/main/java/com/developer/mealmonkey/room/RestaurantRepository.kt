package com.developer.mealmonkey.room

import androidx.lifecycle.LiveData

class RestaurantRepository(private val restaurantDao: RestaurantDao) {


    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }


    val readAllData: LiveData<List<Restaurant>> = restaurantDao.readAllRestaurants()
    val randomRestaurant: LiveData<List<Restaurant>> = restaurantDao.randomRestaurants()

    fun getRestaurant( category:String):LiveData<List<Restaurant>>{
        val getRestaurant: LiveData<List<Restaurant>> = restaurantDao.readAllRestaurantsCategory(category)
        return getRestaurant

    }

    fun searchRestaurant( name:String):LiveData<List<Restaurant>>{
        val searchRes: LiveData<List<Restaurant>> = restaurantDao.searchRestaurant(name)
        return searchRes

    }
}