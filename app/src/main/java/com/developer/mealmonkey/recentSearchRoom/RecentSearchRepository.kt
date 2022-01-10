package com.developer.mealmonkey.recentSearchRoom

import androidx.lifecycle.LiveData

class RecentSearchRepository(private val recentSearchDao: RecentSearchDao) {
    suspend fun addSearch(recentSearch: RecentSearch) {
        recentSearchDao.addSearch(recentSearch)
    }
    val readAllData: LiveData<List<RecentSearch>> = recentSearchDao.readAllRecentSearches()

}