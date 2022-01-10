package com.developer.mealmonkey.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.mealmonkey.cartRoom.Cart
import com.developer.mealmonkey.cartRoom.CartDao
import com.developer.mealmonkey.recentSearchRoom.RecentSearch
import com.developer.mealmonkey.recentSearchRoom.RecentSearchDao

@Database(entities = [Restaurant::class, Cart::class,RecentSearch::class], version = 1,exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun cartDao(): CartDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "Restaurant_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}