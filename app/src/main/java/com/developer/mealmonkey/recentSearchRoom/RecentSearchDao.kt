package com.developer.mealmonkey.recentSearchRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSearch(recentSearch: RecentSearch)

    @Query("SELECT * from recent_table ORDER BY id ASC LIMIT 4")
    fun readAllRecentSearches(): LiveData<List<RecentSearch>>

}