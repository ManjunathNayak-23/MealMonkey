package com.developer.mealmonkey.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    val readAllRestaurant: LiveData<List<Restaurant>>
    val readRandomRestaurant: LiveData<List<Restaurant>>


    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
        readAllRestaurant = repository.readAllData
        readRandomRestaurant = repository.randomRestaurant


    }

    fun getRes(category: String): LiveData<List<Restaurant>> {
        val list = repository.getRestaurant(category)
        return list
    }
    fun searchRes(name: String): LiveData<List<Restaurant>> {
        val list = repository.searchRestaurant(name)
        return list
    }
    fun addRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }


    fun volleyRestaurantRequest(context: Context) {

        val requestQueue = Volley.newRequestQueue(context)

        val url =
            "https://mealmonkey23.000webhostapp.com/MealMonkey/Get%20Restaurants/getRestaurants.php"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    val data = jsonArray.getJSONObject(i)
                    val imgUrl = data.getString("rImage")
                    val id = data.getInt("id")
                    val name = data.getString("rName")
                    val category = data.getString("rCategories")
                    val latitude = data.getDouble("rLatitude")
                    val longitude = data.getDouble("rLongitude")
                    val area = data.getString("rArea")
                    val type = data.getString("rType")
                    val ratings = data.getDouble("rRatings")

                    val model = Restaurant(
                        imgUrl,
                        name,
                        ratings,
                        latitude,
                        longitude,
                        area,
                        type,
                        id,
                        category
                    )
                    addRestaurant(model)


                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)

    }

}