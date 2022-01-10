package com.developer.mealmonkey.recentSearchRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_table")
data class RecentSearch(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val searchName:String
    )