package com.developer.mealmonkey.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addRestaurant(restaurant: Restaurant)

    @Query("SELECT * from restaurant_table ORDER BY rRatings DESC")
    fun readAllRestaurants(): LiveData<List<Restaurant>>


    @Query("SELECT * from restaurant_table WHERE rCategory LIKE '%' || :category || '%'")
    fun readAllRestaurantsCategory(category: String): LiveData<List<Restaurant>>


    @Query("SELECT * FROM restaurant_table ORDER BY RANDOM()")
    fun randomRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant_table WHERE rName LIKE '%' || :name || '%'")
    fun searchRestaurant(name:String): LiveData<List<Restaurant>>




}