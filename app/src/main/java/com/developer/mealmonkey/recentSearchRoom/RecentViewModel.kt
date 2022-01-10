package com.developer.mealmonkey.recentSearchRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.developer.mealmonkey.room.RestaurantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentViewModel(application: Application) : AndroidViewModel(application) {
    val readAllSearch: LiveData<List<RecentSearch>>

    private val repository: RecentSearchRepository


    init {
        val recentSearchDao = RestaurantDatabase.getDatabase(application).recentSearchDao()
        repository = RecentSearchRepository(recentSearchDao)
        readAllSearch = repository.readAllData
    }

    fun addSearch(recentSearch: RecentSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSearch(recentSearch)
        }
    }
}